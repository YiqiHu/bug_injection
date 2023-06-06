 *
 * There's two enum values for the two client encryption mechanisms the AWS
 * S3 SDK supports, even though these are not currently supported in S3A.
 * This is to aid supporting CSE in some form in future, fundamental
 * issues about file length of encrypted data notwithstanding.
 *
  NONE("", false),
  SSE_S3("AES256", true),
  SSE_KMS("SSE-KMS", true),
  SSE_C("SSE-C", true),
  CSE_KMS("CSE-KMS", false),
  CSE_CUSTOM("CSE-CUSTOM", false);
  private String method;
  private boolean serverSide;
  S3AEncryptionMethods(String method, final boolean serverSide) {
      if (v.getMethod().equals(name)) {
