import org.apache.hadoop.fs.PathIOException;
import static org.apache.hadoop.fs.s3a.S3AUtils.longOption;
import static org.apache.hadoop.fs.s3a.impl.InternalConstants.DEFAULT_UPLOAD_PART_COUNT_LIMIT;
import static org.apache.hadoop.fs.s3a.impl.InternalConstants.UPLOAD_PART_COUNT_LIMIT;
   * The part number must be less than 10000.
   * @throws IllegalArgumentException if the parameters are invalid -including
   * @throws PathIOException if the part number is out of range.
      Long offset) throws PathIOException {
    checkArgument(partNumber > 0,
        "partNumber must be between 1 and %s inclusive, but is %s",
            DEFAULT_UPLOAD_PART_COUNT_LIMIT, partNumber);
    long partCountLimit = longOption(conf,
        UPLOAD_PART_COUNT_LIMIT,
        DEFAULT_UPLOAD_PART_COUNT_LIMIT,
        1);
    if (partCountLimit != DEFAULT_UPLOAD_PART_COUNT_LIMIT) {
      LOG.warn("Configuration property {} shouldn't be overridden by client",
              UPLOAD_PART_COUNT_LIMIT);
    }
    final String pathErrorMsg = "Number of parts in multipart upload exceeded."
        + " Current part count = %s, Part count limit = %s ";
    if (partNumber > partCountLimit) {
      throw new PathIOException(destKey,
          String.format(pathErrorMsg, partNumber, partCountLimit));
    }
