import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
  protected void maybeSkipTest() throws IOException {
    String keyId = getS3EncryptionKey(getTestBucketName(getConfiguration()),
        getConfiguration());
