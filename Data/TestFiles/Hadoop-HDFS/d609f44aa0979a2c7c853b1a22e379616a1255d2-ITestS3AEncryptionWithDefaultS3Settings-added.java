import static org.apache.hadoop.fs.s3a.Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
  @SuppressWarnings("deprecation")
        S3_ENCRYPTION_ALGORITHM,
        SERVER_SIDE_ENCRYPTION_ALGORITHM);
    String kmsKey = getS3EncryptionKey(getTestBucketName(c), c);
      String kmsKey = getS3EncryptionKey(getTestBucketName(fs2Conf), fs2Conf);
