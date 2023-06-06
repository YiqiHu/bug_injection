import static org.apache.hadoop.fs.s3a.S3ATestUtils.skipIfEncryptionNotSet;
    String kmsKey = c.get(S3_ENCRYPTION_KEY);
    skipIfEncryptionNotSet(c, getSSEAlgorithm());
