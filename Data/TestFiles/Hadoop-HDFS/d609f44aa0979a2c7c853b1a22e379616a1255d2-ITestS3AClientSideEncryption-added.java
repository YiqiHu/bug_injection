import static org.apache.hadoop.fs.s3a.Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM;
import static org.apache.hadoop.fs.s3a.Constants.SERVER_SIDE_ENCRYPTION_KEY;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.removeBaseAndBucketOverrides;
  @SuppressWarnings("deprecation")
    removeBaseAndBucketOverrides(getTestBucketName(cseDisabledConf),
        cseDisabledConf,
        S3_ENCRYPTION_ALGORITHM,
        S3_ENCRYPTION_KEY,
        SERVER_SIDE_ENCRYPTION_ALGORITHM,
        SERVER_SIDE_ENCRYPTION_KEY);
  protected abstract void maybeSkipTest() throws IOException;
