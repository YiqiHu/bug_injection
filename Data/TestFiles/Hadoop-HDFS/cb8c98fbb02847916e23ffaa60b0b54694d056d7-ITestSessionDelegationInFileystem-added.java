import java.io.UncheckedIOException;
import static org.apache.hadoop.fs.s3a.S3AUtils.getEncryptionAlgorithm;
    String s3EncryptionMethod;
    try {
      s3EncryptionMethod =
          getEncryptionAlgorithm(getTestBucketName(conf), conf).getMethod();
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to lookup encryption algorithm.",
          e);
    }
