import java.util.Collection;
  @Test
  public void testBucketConfigurationPropagation() throws Throwable {
    Configuration config = new Configuration(false);
    setBucketOption(config, "b", "base", "1024");
    String basekey = "fs.s3a.base";
    assertOptionEquals(config, basekey, null);
    String bucketKey = "fs.s3a.bucket.b.base";
    assertOptionEquals(config, bucketKey, "1024");
    Configuration updated = propagateBucketOptions(config, "b");
    assertOptionEquals(updated, basekey, "1024");
    // original conf is not updated
    assertOptionEquals(config, basekey, null);

    String[] sources = updated.getPropertySources(basekey);
    assertEquals(1, sources.length);
    String sourceInfo = sources[0];
    assertTrue("Wrong source " + sourceInfo, sourceInfo.contains(bucketKey));
  }

  @Test
  public void testBucketConfigurationPropagationResolution() throws Throwable {
    Configuration config = new Configuration(false);
    String basekey = "fs.s3a.base";
    String baseref = "fs.s3a.baseref";
    String baseref2 = "fs.s3a.baseref2";
    config.set(basekey, "orig");
    config.set(baseref2, "${fs.s3a.base}");
    setBucketOption(config, "b", basekey, "1024");
    setBucketOption(config, "b", baseref, "${fs.s3a.base}");
    Configuration updated = propagateBucketOptions(config, "b");
    assertOptionEquals(updated, basekey, "1024");
    assertOptionEquals(updated, baseref, "1024");
    assertOptionEquals(updated, baseref2, "1024");
  }

  @Test
  public void testMultipleBucketConfigurations() throws Throwable {
    Configuration config = new Configuration(false);
    setBucketOption(config, "b", USER_AGENT_PREFIX, "UA-b");
    setBucketOption(config, "c", USER_AGENT_PREFIX, "UA-c");
    config.set(USER_AGENT_PREFIX, "UA-orig");
    Configuration updated = propagateBucketOptions(config, "c");
    assertOptionEquals(updated, USER_AGENT_PREFIX, "UA-c");
  }

  @Test
  public void testClearBucketOption() throws Throwable {
    Configuration config = new Configuration();
    config.set(USER_AGENT_PREFIX, "base");
    setBucketOption(config, "bucket", USER_AGENT_PREFIX, "overridden");
    clearBucketOption(config, "bucket", USER_AGENT_PREFIX);
    Configuration updated = propagateBucketOptions(config, "c");
    assertOptionEquals(updated, USER_AGENT_PREFIX, "base");
  }

  @Test
  public void testBucketConfigurationSkipsUnmodifiable() throws Throwable {
    Configuration config = new Configuration(false);
    String impl = "fs.s3a.impl";
    config.set(impl, "orig");
    setBucketOption(config, "b", impl, "b");
    String metastoreImpl = "fs.s3a.metadatastore.impl";
    String ddb = "org.apache.hadoop.fs.s3a.s3guard.DynamoDBMetadataStore";
    setBucketOption(config, "b", metastoreImpl, ddb);
    setBucketOption(config, "b", "impl2", "b2");
    setBucketOption(config, "b", "bucket.b.loop", "b3");
    assertOptionEquals(config, "fs.s3a.bucket.b.impl", "b");

    Configuration updated = propagateBucketOptions(config, "b");
    assertOptionEquals(updated, impl, "orig");
    assertOptionEquals(updated, "fs.s3a.impl2", "b2");
    assertOptionEquals(updated, metastoreImpl, ddb);
    assertOptionEquals(updated, "fs.s3a.bucket.b.loop", null);
  }

  @Test
  public void testSecurityCredentialPropagationNoOverride() throws Exception {
    Configuration config = new Configuration();
    config.set(CREDENTIAL_PROVIDER_PATH, "base");
    patchSecurityCredentialProviders(config);
    assertOptionEquals(config, CREDENTIAL_PROVIDER_PATH,
        "base");
  }

  @Test
  public void testSecurityCredentialPropagationOverrideNoBase()
      throws Exception {
    Configuration config = new Configuration();
    config.unset(CREDENTIAL_PROVIDER_PATH);
    config.set(S3A_SECURITY_CREDENTIAL_PROVIDER_PATH, "override");
    patchSecurityCredentialProviders(config);
    assertOptionEquals(config, CREDENTIAL_PROVIDER_PATH,
        "override");
  }

  @Test
  public void testSecurityCredentialPropagationOverride() throws Exception {
    Configuration config = new Configuration();
    config.set(CREDENTIAL_PROVIDER_PATH, "base");
    config.set(S3A_SECURITY_CREDENTIAL_PROVIDER_PATH, "override");
    patchSecurityCredentialProviders(config);
    assertOptionEquals(config, CREDENTIAL_PROVIDER_PATH,
        "override,base");
    Collection<String> all = config.getStringCollection(
        CREDENTIAL_PROVIDER_PATH);
    assertTrue(all.contains("override"));
    assertTrue(all.contains("base"));
  }

  @Test
  public void testSecurityCredentialPropagationEndToEnd() throws Exception {
    Configuration config = new Configuration();
    config.set(CREDENTIAL_PROVIDER_PATH, "base");
    setBucketOption(config, "b", S3A_SECURITY_CREDENTIAL_PROVIDER_PATH,
        "override");
    Configuration updated = propagateBucketOptions(config, "b");

    patchSecurityCredentialProviders(updated);
    assertOptionEquals(updated, CREDENTIAL_PROVIDER_PATH,
        "override,base");
  }

