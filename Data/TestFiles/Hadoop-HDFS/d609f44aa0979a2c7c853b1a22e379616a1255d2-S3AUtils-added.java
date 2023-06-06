import org.apache.hadoop.fs.s3a.auth.delegation.EncryptionSecrets;
import java.io.UncheckedIOException;
   * Lookup a per-bucket-secret from a configuration including JCEKS files.
   * No attempt is made to look for the global configuration.
   * @param bucket bucket or "" if none known
   * @param conf configuration
   * @param baseKey base key to look up, e.g "fs.s3a.secret.key"
   * @return the secret or null.
   * @throws IOException on any IO problem
   * @throws IllegalArgumentException bad arguments
   */
  private static String lookupBucketSecret(
      String bucket,
      Configuration conf,
      String baseKey)
      throws IOException {

    Preconditions.checkArgument(!isEmpty(bucket), "null/empty bucket argument");
    Preconditions.checkArgument(baseKey.startsWith(FS_S3A_PREFIX),
        "%s does not start with $%s", baseKey, FS_S3A_PREFIX);
    String subkey = baseKey.substring(FS_S3A_PREFIX.length());

    // set from the long key unless overidden.
    String longBucketKey = String.format(
        BUCKET_PATTERN, bucket, baseKey);
    String initialVal = getPassword(conf, longBucketKey, null, null);
    // then override from the short one if it is set
    String shortBucketKey = String.format(
        BUCKET_PATTERN, bucket, subkey);
    return getPassword(conf, shortBucketKey, initialVal, null);
  }

  /**
   * Get any S3 encryption key, without propagating exceptions from
   * JCEKs files.
  public static String getS3EncryptionKey(
      String bucket,
      return getS3EncryptionKey(bucket, conf, false);
      // never going to happen, but to make sure, covert to
      // runtime exception
      throw new UncheckedIOException(e);
    }
  }

    /**
     * Get any SSE/CSE key from a configuration/credential provider.
     * This operation handles the case where the option has been
     * set in the provider or configuration to the option
     * {@code SERVER_SIDE_ENCRYPTION_KEY}.
     * IOExceptions raised during retrieval are swallowed.
     * @param bucket bucket to query for
     * @param conf configuration to examine
     * @param propagateExceptions should IO exceptions be rethrown?
     * @return the encryption key or ""
     * @throws IllegalArgumentException bad arguments.
     * @throws IOException if propagateExceptions==true and reading a JCEKS file raised an IOE
     */
  @SuppressWarnings("deprecation")
  public static String getS3EncryptionKey(
      String bucket,
      Configuration conf,
      boolean propagateExceptions) throws IOException {
    try {
      // look up the per-bucket value of the new key,
      // which implicitly includes the deprecation remapping
      String key = lookupBucketSecret(bucket, conf, S3_ENCRYPTION_KEY);
      if (key == null) {
        // old key in bucket, jceks
        key = lookupBucketSecret(bucket, conf, SERVER_SIDE_ENCRYPTION_KEY);
      }
      if (key == null) {
        // new key, global; implicit translation of old key in XML files.
        key = lookupPassword(null, conf, S3_ENCRYPTION_KEY);
      }
      if (key == null) {
        // old key, JCEKS
        key = lookupPassword(null, conf, SERVER_SIDE_ENCRYPTION_KEY);
      }
      if (key == null) {
        // no key, return ""
        key = "";
      }
      return key;
    } catch (IOException e) {
      if (propagateExceptions) {
        throw e;
      }
      LOG.warn("Cannot retrieve {} for bucket {}",
          S3_ENCRYPTION_KEY, bucket, e);
   * @throws IOException on JCKES lookup or invalid method/key configuration.
    return buildEncryptionSecrets(bucket, conf).getEncryptionMethod();
  }

  /**
   * Get the server-side encryption or client side encryption algorithm.
   * This includes validation of the configuration, checking the state of
   * the encryption key given the chosen algorithm.
   *
   * @param bucket bucket to query for
   * @param conf configuration to scan
   * @return the encryption mechanism (which will be {@code NONE} unless
   * one is set and secrets.
   * @throws IOException on JCKES lookup or invalid method/key configuration.
   */
  @SuppressWarnings("deprecation")
  public static EncryptionSecrets buildEncryptionSecrets(String bucket,
      Configuration conf) throws IOException {

    // new key, per-bucket
    // this will include fixup of the old key in config XML entries
    String algorithm = lookupBucketSecret(bucket, conf, S3_ENCRYPTION_ALGORITHM);
    if (algorithm == null) {
      // try the old key, per-bucket setting, which will find JCEKS values
      algorithm = lookupBucketSecret(bucket, conf, SERVER_SIDE_ENCRYPTION_ALGORITHM);
    }
    if (algorithm == null) {
      // new key, global setting
      // this will include fixup of the old key in config XML entries
      algorithm = lookupPassword(null, conf, S3_ENCRYPTION_ALGORITHM);
    }
    if (algorithm == null) {
      // old key, global setting, for JCEKS entries.
      algorithm = lookupPassword(null, conf, SERVER_SIDE_ENCRYPTION_ALGORITHM);
    }
    // now determine the algorithm
    final S3AEncryptionMethods encryptionMethod = S3AEncryptionMethods.getMethod(algorithm);

    // look up the encryption key
    String encryptionKey = getS3EncryptionKey(bucket, conf,
        encryptionMethod.requiresSecret());
    return new EncryptionSecrets(encryptionMethod, encryptionKey);
