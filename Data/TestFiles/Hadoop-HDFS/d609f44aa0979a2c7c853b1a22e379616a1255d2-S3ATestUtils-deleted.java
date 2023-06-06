  private static void skipIfS3GuardAndS3CSEEnabled(Configuration conf) {
    String encryptionMethod =
        conf.getTrimmed(Constants.S3_ENCRYPTION_ALGORITHM, "");
    assertEquals("Value of " + key, expected, conf.get(key));
      S3AEncryptionMethods s3AEncryptionMethod) {
    if (!configuration.getTrimmed(S3_ENCRYPTION_ALGORITHM, "")
        .equals(s3AEncryptionMethod.getMethod())
        || configuration.get(Constants.S3_ENCRYPTION_KEY) == null) {
          + s3AEncryptionMethod.getMethod());
