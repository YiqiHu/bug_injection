import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3a.UnknownStoreException;
import static org.apache.hadoop.fs.s3a.Constants.S3A_BUCKET_PROBE;
import static org.apache.hadoop.fs.s3a.Constants.S3A_BUCKET_PROBE_DEFAULT;
import static org.apache.hadoop.fs.s3a.Constants.S3_METADATA_STORE_IMPL;
import static org.apache.hadoop.fs.s3a.Constants.S3GUARD_METASTORE_LOCAL;
import static org.apache.hadoop.fs.s3a.S3ATestUtils.removeBaseAndBucketOverrides;
  @Override
  protected Configuration createConfiguration() {
    Configuration conf = super.createConfiguration();
    removeBaseAndBucketOverrides(conf,
        S3_METADATA_STORE_IMPL, S3A_BUCKET_PROBE);
    conf.set(S3_METADATA_STORE_IMPL, S3GUARD_METASTORE_LOCAL);
    conf.setInt(S3A_BUCKET_PROBE, S3A_BUCKET_PROBE_DEFAULT);
    return conf;
  }

    assertTrue("metadata store impl should be LocalMetadataStore.",
        getMetadataStore() instanceof LocalMetadataStore);
    intercept(UnknownStoreException.class,
