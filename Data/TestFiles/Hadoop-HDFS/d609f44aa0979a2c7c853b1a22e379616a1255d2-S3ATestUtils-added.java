import org.apache.hadoop.fs.s3a.auth.delegation.EncryptionSecrets;
import org.assertj.core.api.Assertions;
import static org.apache.hadoop.fs.s3a.S3AUtils.buildEncryptionSecrets;
import static org.apache.hadoop.fs.s3a.S3AUtils.getEncryptionAlgorithm;
  private static void skipIfS3GuardAndS3CSEEnabled(Configuration conf)
      throws IOException {
    String encryptionMethod = getEncryptionAlgorithm(getTestBucketName(conf),
        conf).getMethod();
    String actual = conf.get(key);
    String origin = actual == null
        ? "(none)"
        : "[" + StringUtils.join(conf.getPropertySources(key), ", ") + "]";
    Assertions.assertThat(actual)
        .describedAs("Value of %s with origin %s", key, origin)
        .isEqualTo(expected);
      S3AEncryptionMethods s3AEncryptionMethod) throws IOException {
    String bucket = getTestBucketName(configuration);
    final EncryptionSecrets secrets = buildEncryptionSecrets(bucket, configuration);
    if (!s3AEncryptionMethod.getMethod().equals(secrets.getEncryptionMethod().getMethod())
        || StringUtils.isBlank(secrets.getEncryptionKey())) {
          + s3AEncryptionMethod.getMethod()
          + " in " + secrets);
