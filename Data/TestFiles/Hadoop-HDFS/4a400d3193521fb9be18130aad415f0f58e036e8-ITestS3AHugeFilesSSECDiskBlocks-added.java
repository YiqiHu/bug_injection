import static org.apache.hadoop.fs.s3a.Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM;
import static org.apache.hadoop.fs.s3a.Constants.SERVER_SIDE_ENCRYPTION_KEY;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.removeBaseAndBucketOverrides;
    removeBaseAndBucketOverrides(conf, SERVER_SIDE_ENCRYPTION_KEY,
            SERVER_SIDE_ENCRYPTION_ALGORITHM);
