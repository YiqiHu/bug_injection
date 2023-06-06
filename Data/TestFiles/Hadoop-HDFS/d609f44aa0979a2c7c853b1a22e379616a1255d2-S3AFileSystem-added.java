      setEncryptionSecrets(
          buildEncryptionSecrets(bucket, conf));
      isCSEEnabled = S3AEncryptionMethods.CSE_KMS.getMethod()
          .equals(getS3EncryptionAlgorithm().getMethod());
