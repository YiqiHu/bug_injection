   * Get any SSE/CSE key from a configuration/credential provider.
   * This operation handles the case where the option has been
   * set in the provider or configuration to the option
   * {@code OLD_S3A_SERVER_SIDE_ENCRYPTION_KEY}.
   * IOExceptions raised during retrieval are swallowed.
  public static String getS3EncryptionKey(String bucket,
      return lookupPassword(bucket, conf, Constants.S3_ENCRYPTION_KEY);
      LOG.error("Cannot retrieve " + Constants.S3_ENCRYPTION_KEY, e);
   * @throws IOException on any validation problem.
    S3AEncryptionMethods encryptionMethod = S3AEncryptionMethods.getMethod(
        lookupPassword(bucket, conf,
            Constants.S3_ENCRYPTION_ALGORITHM));
    String encryptionKey = getS3EncryptionKey(bucket, conf);
    return encryptionMethod;
