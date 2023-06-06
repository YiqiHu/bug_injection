
  @Test
  public void testInternalDirectoryPermissions() throws IOException {
    LOG.info("Starting testInternalDirectoryPermissions!");
    Configuration localConf = new Configuration(conf);
    ConfigUtil.addLinkFallback(
        localConf, new Path(targetTestRoot, "fallbackDir").toUri());
    FileSystem fs = FileSystem.get(FsConstants.VIEWFS_URI, localConf);
    // check that the default permissions on a sub-folder of an internal
    // directory are the same as those created on non-internal directories.
    Path subDirOfInternalDir = new Path("/internalDir/dir1");
    fs.mkdirs(subDirOfInternalDir);

    Path subDirOfRealDir = new Path("/internalDir/linkToDir2/dir1");
    fs.mkdirs(subDirOfRealDir);

    assertEquals(fs.getFileStatus(subDirOfInternalDir).getPermission(),
        fs.getFileStatus(subDirOfRealDir).getPermission());
  }
