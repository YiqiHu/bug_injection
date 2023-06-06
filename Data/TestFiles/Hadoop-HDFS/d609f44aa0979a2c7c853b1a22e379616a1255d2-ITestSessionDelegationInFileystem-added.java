import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
    String s3EncryptionKey = getS3EncryptionKey(getTestBucketName(conf), conf);
