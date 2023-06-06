import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.apache.hadoop.fs.azurebfs.contracts.exceptions.AbfsRestOperationException;
import org.apache.hadoop.fs.azurebfs.services.AbfsClient;
import org.apache.hadoop.fs.azurebfs.services.AbfsHttpOperation;
import org.apache.hadoop.fs.azurebfs.services.AbfsRestOperation;
import org.apache.hadoop.fs.azurebfs.services.TestAbfsClient;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.UUID.randomUUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.apache.hadoop.fs.azurebfs.constants.FileSystemConfigurations.DEFAULT_CLOCK_SKEW_WITH_SERVER_IN_MS;
import static org.apache.hadoop.fs.contract.ContractTestUtils.assertIsFile;

import static org.apache.hadoop.test.LambdaTestUtils.intercept;
  private static final int REDUCED_RETRY_COUNT = 1;
  private static final int REDUCED_MAX_BACKOFF_INTERVALS_MS = 5000;

  @Test
  public void testRenameRetryFailureAsHTTP400() throws Exception {
    // Rename failed as Bad Request
    // RenameIdempotencyCheck should throw back the rename failure Op
    testRenameTimeout(HTTP_BAD_REQUEST, HTTP_BAD_REQUEST, false,
        "renameIdempotencyCheckOp should return rename BadRequest "
            + "response itself.");
  }

  @Test
  public void testRenameRetryFailureAsHTTP404() throws Exception {
    // Rename failed as FileNotFound and the destination LMT is
    // within TimespanForIdentifyingRecentOperationThroughLMT
    testRenameTimeout(HTTP_NOT_FOUND, HTTP_OK, false,
        "Rename should return success response because the destination "
            + "path is present and its LMT is within "
            + "TimespanForIdentifyingRecentOperationThroughLMT.");
  }

  @Test
  public void testRenameRetryFailureWithDestOldLMT() throws Exception {
    // Rename failed as FileNotFound and the destination LMT is
    // older than TimespanForIdentifyingRecentOperationThroughLMT
    testRenameTimeout(HTTP_NOT_FOUND, HTTP_NOT_FOUND, true,
        "Rename should return original rename failure response "
            + "because the destination path LMT is older than "
            + "TimespanForIdentifyingRecentOperationThroughLMT.");
  }

  @Test
  public void testRenameIdempotencyTriggerHttpNotFound() throws Exception {
    AbfsHttpOperation http404Op = mock(AbfsHttpOperation.class);
    when(http404Op.getStatusCode()).thenReturn(HTTP_NOT_FOUND);

    AbfsHttpOperation http200Op = mock(AbfsHttpOperation.class);
    when(http200Op.getStatusCode()).thenReturn(HTTP_OK);

    // Check 1 where idempotency check fails to find dest path
    // Rename should throw exception
    testRenameIdempotencyTriggerChecks(http404Op);

    // Check 2 where idempotency check finds the dest path
    // Renam will be successful
    testRenameIdempotencyTriggerChecks(http200Op);
  }

  private void testRenameIdempotencyTriggerChecks(
      AbfsHttpOperation idempotencyRetHttpOp) throws Exception {

    final AzureBlobFileSystem fs = getFileSystem();
    AbfsClient client = TestAbfsClient.getMockAbfsClient(
        fs.getAbfsStore().getClient(),
        this.getConfiguration());

    AbfsRestOperation idempotencyRetOp = mock(AbfsRestOperation.class);
    when(idempotencyRetOp.getResult()).thenReturn(idempotencyRetHttpOp);
    doReturn(idempotencyRetOp).when(client).renameIdempotencyCheckOp(any(),
        any(), any(), any());
    when(client.renamePath(any(), any(), any(), any())).thenCallRealMethod();

    // rename on non-existing source file will trigger idempotency check
    if (idempotencyRetHttpOp.getStatusCode() == HTTP_OK) {
      // idempotency check found that destination exists and is recently created
      Assertions.assertThat(client.renamePath(
          "/NonExistingsourcepath",
          "/destpath",
          null,
          getTestTracingContext(fs, true))
          .getResult()
          .getStatusCode())
          .describedAs("Idempotency check reports recent successful "
              + "rename. 200OK should be returned")
          .isEqualTo(idempotencyRetOp.getResult().getStatusCode());
    } else {
      // rename dest not found. Original exception should be returned.
      intercept(AbfsRestOperationException.class,
          () -> client.renamePath(
              "/NonExistingsourcepath",
              "/destpath",
              "",
              getTestTracingContext(fs, true)));
    }
  }

  private void testRenameTimeout(
      int renameRequestStatus,
      int renameIdempotencyCheckStatus,
      boolean isOldOp,
      String assertMessage) throws Exception {
    // Config to reduce the retry and maxBackoff time for test run
    AbfsConfiguration abfsConfig
        = TestAbfsConfigurationFieldsValidation.updateRetryConfigs(
        getConfiguration(),
        REDUCED_RETRY_COUNT, REDUCED_MAX_BACKOFF_INTERVALS_MS);

    final AzureBlobFileSystem fs = getFileSystem();
    AbfsClient abfsClient = fs.getAbfsStore().getClient();
    AbfsClient testClient = TestAbfsClient.createTestClientFromCurrentContext(
        abfsClient,
        abfsConfig);

    // Mock instance of AbfsRestOperation
    AbfsRestOperation op = mock(AbfsRestOperation.class);
    // Set retryCount to non-zero
    when(op.isARetriedRequest()).thenReturn(true);

    // Mock instance of Http Operation response. This will return HTTP:Bad Request
    AbfsHttpOperation http400Op = mock(AbfsHttpOperation.class);
    when(http400Op.getStatusCode()).thenReturn(HTTP_BAD_REQUEST);

    // Mock instance of Http Operation response. This will return HTTP:Not Found
    AbfsHttpOperation http404Op = mock(AbfsHttpOperation.class);
    when(http404Op.getStatusCode()).thenReturn(HTTP_NOT_FOUND);

    Path destinationPath = fs.makeQualified(
        new Path("destination" + randomUUID().toString()));

    Instant renameRequestStartTime = Instant.now();

    if (renameRequestStatus == HTTP_BAD_REQUEST) {
      when(op.getResult()).thenReturn(http400Op);
    } else if (renameRequestStatus == HTTP_NOT_FOUND) {
      // Create the file new.
      fs.create(destinationPath).close();
      when(op.getResult()).thenReturn(http404Op);

      if (isOldOp) {
        // instead of sleeping for DEFAULT_CLOCK_SKEW_WITH_SERVER_IN_MS
        // which will affect test run time
        // will modify renameRequestStartTime to a future time so that
        // lmt will qualify for old op
        renameRequestStartTime = renameRequestStartTime.plusSeconds(
            DEFAULT_CLOCK_SKEW_WITH_SERVER_IN_MS);
      }

    }

    Assertions.assertThat(testClient.renameIdempotencyCheckOp(
        renameRequestStartTime,
        op,
        destinationPath.toUri().getPath(),
        getTestTracingContext(fs, true))
        .getResult()
        .getStatusCode())
        .describedAs(assertMessage)
        .isEqualTo(renameIdempotencyCheckStatus);
  }

