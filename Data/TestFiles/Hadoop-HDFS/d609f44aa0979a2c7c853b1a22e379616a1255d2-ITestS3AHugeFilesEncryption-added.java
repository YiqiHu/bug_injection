import org.apache.commons.lang.StringUtils;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
    return StringUtils.isNotBlank(getS3EncryptionKey(getTestBucketName(c), c));
    String kmsKey = getS3EncryptionKey(getTestBucketName(c), c);
