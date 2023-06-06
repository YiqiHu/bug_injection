import static org.apache.hadoop.fs.s3a.Constants.S3_ENCRYPTION_ALGORITHM;
import static org.apache.hadoop.fs.s3a.Constants.S3_ENCRYPTION_KEY;
  protected void verifyWrittenBytes(FileSystem.Statistics stats) {
    if (conf.get(S3_ENCRYPTION_ALGORITHM, "")
        .equals(S3AEncryptionMethods.CSE_KMS.getMethod())) {
      String keyId = conf.get(S3_ENCRYPTION_KEY, "");
