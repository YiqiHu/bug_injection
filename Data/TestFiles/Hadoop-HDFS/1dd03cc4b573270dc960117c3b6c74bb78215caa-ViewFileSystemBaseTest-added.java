import static org.apache.hadoop.fs.viewfs.Constants.CONFIG_VIEWFS_ENABLE_INNER_CACHE;
    viewFs.resolvePath(
        new Path(String.format("viewfs://%s/%s", clusterName, "/user")));

  @Test
  public void testTargetFileSystemLazyInitialization() throws Exception {
    final String clusterName = "cluster" + new Random().nextInt();
    Configuration config = new Configuration(conf);
    config.setBoolean(CONFIG_VIEWFS_ENABLE_INNER_CACHE, false);
    config.setClass("fs.mockfs.impl",
        TestChRootedFileSystem.MockFileSystem.class, FileSystem.class);
    ConfigUtil.addLink(config, clusterName, "/user",
        URI.create("mockfs://mockauth1/mockpath"));
    ConfigUtil.addLink(config, clusterName,
        "/mock", URI.create("mockfs://mockauth/mockpath"));

    final int cacheSize = TestFileUtil.getCacheSize();
    ViewFileSystem viewFs = (ViewFileSystem) FileSystem
        .get(new URI("viewfs://" + clusterName + "/"), config);

    // As no inner file system instance has been initialized,
    // cache size will remain the same
    // cache is disabled for viewfs scheme, so the viewfs:// instance won't
    // go in the cache even after the initialization
    assertEquals(cacheSize, TestFileUtil.getCacheSize());

    // This resolve path will initialize the file system corresponding
    // to the mount table entry of the path "/user"
    viewFs.resolvePath(
        new Path(String.format("viewfs://%s/%s", clusterName, "/user")));

    // Cache size will increase by 1.
    assertEquals(cacheSize + 1, TestFileUtil.getCacheSize());
    // This resolve path will initialize the file system corresponding
    // to the mount table entry of the path "/mock"
    viewFs.resolvePath(new Path(String.format("viewfs://%s/%s", clusterName,
        "/mock")));
    // One more file system instance will get initialized.
    assertEquals(cacheSize + 2, TestFileUtil.getCacheSize());
    viewFs.close();
    // Initialized FileSystem instances will not be removed from cache as
    // viewfs inner cache is disabled
    assertEquals(cacheSize + 2, TestFileUtil.getCacheSize());
  }
