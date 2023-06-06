
  @Test
  public void testTargetFileSystemLazyInitializationForChecksumMethods()
      throws Exception {
    final String clusterName = "cluster" + new Random().nextInt();
    Configuration config = new Configuration(conf);
    config.setBoolean(CONFIG_VIEWFS_ENABLE_INNER_CACHE, false);
    config.setClass("fs.othermockfs.impl",
        TestChRootedFileSystem.MockFileSystem.class, FileSystem.class);
    ConfigUtil.addLink(config, clusterName, "/user",
        URI.create("othermockfs://mockauth1/mockpath"));
    ConfigUtil.addLink(config, clusterName,
        "/mock", URI.create("othermockfs://mockauth/mockpath"));

    final int cacheSize = TestFileUtil.getCacheSize();
    ViewFileSystem viewFs = (ViewFileSystem) FileSystem.get(
        new URI("viewfs://" + clusterName + "/"), config);

    // As no inner file system instance has been initialized,
    // cache size will remain the same
    // cache is disabled for viewfs scheme, so the viewfs:// instance won't
    // go in the cache even after the initialization
    assertEquals(cacheSize, TestFileUtil.getCacheSize());

    // This is not going to initialize any filesystem instance
    viewFs.setVerifyChecksum(true);

    // Cache size will remain the same
    assertEquals(cacheSize, TestFileUtil.getCacheSize());

    // This resolve path will initialize the file system corresponding
    // to the mount table entry of the path "/user"
    viewFs.getFileChecksum(
        new Path(String.format("viewfs://%s/%s", clusterName, "/user")));

    // Cache size will increase by 1.
    assertEquals(cacheSize + 1, TestFileUtil.getCacheSize());

    viewFs.close();
    // Initialized FileSystem instances will not be removed from cache as
    // viewfs inner cache is disabled
    assertEquals(cacheSize + 1, TestFileUtil.getCacheSize());
  }
