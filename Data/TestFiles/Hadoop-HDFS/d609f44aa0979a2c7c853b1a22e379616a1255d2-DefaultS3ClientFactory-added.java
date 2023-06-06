import static org.apache.hadoop.fs.s3a.S3AUtils.getEncryptionAlgorithm;
import static org.apache.hadoop.fs.s3a.S3AUtils.getS3EncryptionKey;
  /** Bucket name. */
  private String bucket;

    bucket = uri.getHost();
            bucket,
    // Get the encryption method for this bucket.
    S3AEncryptionMethods encryptionMethods =
        getEncryptionAlgorithm(bucket, conf);
      // If CSE is enabled then build a S3EncryptionClient.
      if (S3AEncryptionMethods.CSE_KMS.getMethod()
          .equals(encryptionMethods.getMethod())) {
    // CSE-KMS Method
    String kmsKeyId = getS3EncryptionKey(bucket, conf, true);
    Preconditions.checkArgument(!StringUtils.isBlank(kmsKeyId), "CSE-KMS "
        + "method requires KMS key ID. Use " + S3_ENCRYPTION_KEY
