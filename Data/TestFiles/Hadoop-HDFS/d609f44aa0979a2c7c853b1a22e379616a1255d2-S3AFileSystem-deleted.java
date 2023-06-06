      setEncryptionSecrets(new EncryptionSecrets(
          getEncryptionAlgorithm(bucket, conf),
          getS3EncryptionKey(bucket, getConf())));
      isCSEEnabled = S3AUtils.lookupPassword(conf,
          Constants.S3_ENCRYPTION_ALGORITHM, "")
          .equals(S3AEncryptionMethods.CSE_KMS.getMethod());
