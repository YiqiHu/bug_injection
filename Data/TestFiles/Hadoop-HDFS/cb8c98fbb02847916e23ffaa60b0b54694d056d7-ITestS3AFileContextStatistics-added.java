import java.io.IOException;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3AUtils.getEncryptionAlgorithm;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
  protected void verifyWrittenBytes(FileSystem.Statistics stats)
      throws IOException {
    if (S3AEncryptionMethods.CSE_KMS.getMethod()
        .equals(getEncryptionAlgorithm(getTestBucketName(conf), conf)
            .getMethod())) {
      String keyId = getS3EncryptionKey(getTestBucketName(conf), conf);
