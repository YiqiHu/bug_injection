import static org.apache.hadoop.fs.s3a.Constants.S3_ENCRYPTION_KEY;
        S3_ENCRYPTION_ALGORITHM);
    String kmsKey = c.getTrimmed(S3_ENCRYPTION_KEY);
      String kmsKey = fs2Conf.getTrimmed(S3_ENCRYPTION_KEY);
