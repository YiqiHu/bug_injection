import static org.apache.hadoop.fs.s3a.Constants.S3_ENCRYPTION_ALGORITHM;
            uri.getHost(),
      if (S3AEncryptionMethods.getMethod(S3AUtils.
          lookupPassword(conf, S3_ENCRYPTION_ALGORITHM, null))
          .equals(S3AEncryptionMethods.CSE_KMS)) {
    //CSE-KMS Method
    String kmsKeyId = S3AUtils.lookupPassword(conf,
        S3_ENCRYPTION_KEY, null);
    Preconditions.checkArgument(kmsKeyId != null, "CSE-KMS method "
        + "requires KMS key ID. Use " + S3_ENCRYPTION_KEY
