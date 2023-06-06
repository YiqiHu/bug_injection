import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.PathIOException;
import java.io.ByteArrayInputStream;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;
import static org.mockito.Mockito.when;
    stream.flush();
  }

  @Test
  public void testWriteOperationHelperPartLimits() throws Throwable {
    S3AFileSystem s3a = mock(S3AFileSystem.class);
    when(s3a.getBucket()).thenReturn("bucket");
    WriteOperationHelper woh = new WriteOperationHelper(s3a,
        new Configuration());
    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        "a".getBytes());
    // first one works
    String key = "destKey";
    woh.newUploadPartRequest(key,
        "uploadId", 1, 1024, inputStream, null, 0L);
    // but ask past the limit and a PathIOE is raised
    intercept(PathIOException.class, key,
        () -> woh.newUploadPartRequest(key,
            "uploadId", 50000, 1024, inputStream, null, 0L));
