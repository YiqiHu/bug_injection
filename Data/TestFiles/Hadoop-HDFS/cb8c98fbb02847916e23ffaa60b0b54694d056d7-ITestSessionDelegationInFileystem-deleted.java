import org.apache.hadoop.fs.s3a.S3AEncryptionMethods;
    String s3EncryptionMethod =
        conf.getTrimmed(Constants.S3_ENCRYPTION_ALGORITHM,
            S3AEncryptionMethods.SSE_KMS.getMethod());
