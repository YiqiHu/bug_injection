import java.time.Instant;
import org.apache.hadoop.fs.azurebfs.utils.DateTimeUtils;
    Instant renameRequestStartTime = Instant.now();
    try {
      op.execute(tracingContext);
    } catch (AzureBlobFileSystemException e) {
        // If we have no HTTP response, throw the original exception.
        if (!op.hasResult()) {
          throw e;
        }
        final AbfsRestOperation idempotencyOp = renameIdempotencyCheckOp(
            renameRequestStartTime, op, destination, tracingContext);
        if (idempotencyOp.getResult().getStatusCode()
            == op.getResult().getStatusCode()) {
          // idempotency did not return different result
          // throw back the exception
          throw e;
        } else {
          return idempotencyOp;
        }
    }

    return op;
  }

  /**
   * Check if the rename request failure is post a retry and if earlier rename
   * request might have succeeded at back-end.
   *
   * If there is a parallel rename activity happening from any other store
   * interface, the logic here will detect the rename to have happened due to
   * the one initiated from this ABFS filesytem instance as it was retried. This
   * should be a corner case hence going ahead with LMT check.
   * @param renameRequestStartTime startTime for the rename request
   * @param op Rename request REST operation response with non-null HTTP response
   * @param destination rename destination path
   * @param tracingContext Tracks identifiers for request header
   * @return REST operation response post idempotency check
   * @throws AzureBlobFileSystemException if GetFileStatus hits any exception
   */
  public AbfsRestOperation renameIdempotencyCheckOp(
      final Instant renameRequestStartTime,
      final AbfsRestOperation op,
      final String destination,
      TracingContext tracingContext) throws AzureBlobFileSystemException {
    Preconditions.checkArgument(op.hasResult(), "Operations has null HTTP response");
    if ((op.isARetriedRequest())
        && (op.getResult().getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND)) {
      // Server has returned HTTP 404, which means rename source no longer
      // exists. Check on destination status and if it has a recent LMT timestamp.
      // If yes, return success, else fall back to original rename request failure response.

      try {
        final AbfsRestOperation destStatusOp = getPathStatus(destination,
            false, tracingContext);
        if (destStatusOp.getResult().getStatusCode()
            == HttpURLConnection.HTTP_OK) {
          String lmt = destStatusOp.getResult().getResponseHeader(
              HttpHeaderConfigurations.LAST_MODIFIED);

          if (DateTimeUtils.isRecentlyModified(lmt, renameRequestStartTime)) {
            LOG.debug("Returning success response from rename idempotency logic");
            return destStatusOp;
          }
        }
      } catch (AzureBlobFileSystemException e) {
        // GetFileStatus on the destination failed, return original op
        return op;
      }
    }

