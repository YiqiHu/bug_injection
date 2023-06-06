  NONE("", false, false),
  SSE_S3("AES256", true, false),
  SSE_KMS("SSE-KMS", true, false),
  SSE_C("SSE-C", true, true),
  CSE_KMS("CSE-KMS", false, true),
  CSE_CUSTOM("CSE-CUSTOM", false, true);
  /**
   * Error string when {@link #getMethod(String)} fails.
   * Used in tests.
   */
  /**
   * What is the encryption method?
   */
  private final String method;

  /**
   * Is this server side?
   */
  private final boolean serverSide;

  /**
   * Does the encryption method require a
   * secret in the encryption.key property?
   */
  private final boolean requiresSecret;
  S3AEncryptionMethods(String method,
      final boolean serverSide,
      final boolean requiresSecret) {
    this.requiresSecret = requiresSecret;
  /**
   * Does this encryption algorithm require a secret?
   * @return true if a secret must be retrieved.
   */
  public boolean requiresSecret() {
    return requiresSecret;
  }

      if (v.getMethod().equalsIgnoreCase(name)) {
