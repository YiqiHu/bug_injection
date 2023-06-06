import org.apache.commons.lang.StringUtils;
import static org.apache.hadoop.fs.contract.ContractTestUtils.skip;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.getTestBucketName;
    String kmsKey = S3AUtils.getS3EncryptionKey(getTestBucketName(c), c);
    if (StringUtils.isBlank(kmsKey)) {
      skip(S3_ENCRYPTION_KEY + " is not set for " +
          SSE_KMS.getMethod());
    }
