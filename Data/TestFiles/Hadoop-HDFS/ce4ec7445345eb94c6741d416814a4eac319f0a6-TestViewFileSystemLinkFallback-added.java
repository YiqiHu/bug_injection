import java.util.HashSet;

  /**
   * This tests whether the fallback link gets listed for list operation
   * of root directory of mount table.
   * @throws Exception
   */
  @Test
  public void testListingWithFallbackLink() throws Exception {
    Path dir1 = new Path(targetTestRoot, "fallbackDir/dir1");
    fsTarget.mkdirs(dir1);
    String clusterName = Constants.CONFIG_VIEWFS_DEFAULT_MOUNT_TABLE;
    URI viewFsUri = new URI(FsConstants.VIEWFS_SCHEME, clusterName,
        "/", null, null);

    HashSet<Path> beforeFallback = new HashSet<>();
    try(FileSystem vfs = FileSystem.get(viewFsUri, conf)) {
      for (FileStatus stat : vfs.listStatus(new Path(viewFsUri.toString()))) {
        beforeFallback.add(stat.getPath());
      }
    }

    ConfigUtil.addLinkFallback(conf, clusterName,
        new Path(targetTestRoot, "fallbackDir").toUri());

    try (FileSystem vfs = FileSystem.get(viewFsUri, conf)) {
      HashSet<Path> afterFallback = new HashSet<>();
      for (FileStatus stat : vfs.listStatus(new Path(viewFsUri.toString()))) {
        afterFallback.add(stat.getPath());
      }
      afterFallback.removeAll(beforeFallback);
      assertTrue("Listing didn't include fallback link",
          afterFallback.size() == 1);
      Path[] fallbackArray = new Path[afterFallback.size()];
      afterFallback.toArray(fallbackArray);
      Path expected = new Path(viewFsUri.toString(), "dir1");
      assertEquals("Path did not match",
          expected, fallbackArray[0]);

      // Create a directory using the returned fallback path and verify
      Path childDir = new Path(fallbackArray[0], "child");
      vfs.mkdirs(childDir);
      FileStatus status = fsTarget.getFileStatus(new Path(dir1, "child"));
      assertTrue(status.isDirectory());
      assertTrue(vfs.getFileStatus(childDir).isDirectory());
    }
  }

  /**
   * This tests whether fallback directory gets shaded during list operation
   * of root directory of mount table when the same directory name exists as
   * mount point as well as in the fallback linked directory.
   * @throws Exception
   */
  @Test
  public void testListingWithFallbackLinkWithSameMountDirectories()
      throws Exception {
    // Creating two directories under the fallback directory.
    // "user" directory already exists as configured mount point.
    Path dir1 = new Path(targetTestRoot, "fallbackDir/user");
    Path dir2 = new Path(targetTestRoot, "fallbackDir/user1");
    fsTarget.mkdirs(dir1);
    fsTarget.mkdirs(dir2);
    String clusterName = Constants.CONFIG_VIEWFS_DEFAULT_MOUNT_TABLE;
    URI viewFsUri = new URI(FsConstants.VIEWFS_SCHEME, clusterName,
        "/", null, null);

    HashSet<Path> beforeFallback = new HashSet<>();
    try(FileSystem vfs = FileSystem.get(viewFsUri, conf)) {
      for (FileStatus stat : vfs.listStatus(new Path(viewFsUri.toString()))) {
        beforeFallback.add(stat.getPath());
      }
    }
    ConfigUtil.addLinkFallback(conf, clusterName,
        new Path(targetTestRoot, "fallbackDir").toUri());

    try (FileSystem vfs = FileSystem.get(viewFsUri, conf)) {
      HashSet<Path> afterFallback = new HashSet<>();
      for (FileStatus stat : vfs.listStatus(new Path(viewFsUri.toString()))) {
        afterFallback.add(stat.getPath());
      }
      afterFallback.removeAll(beforeFallback);
      assertTrue("The same directory name in fallback link should be shaded",
          afterFallback.size() == 1);
      Path[] fallbackArray = new Path[afterFallback.size()];
      // Only user1 should be listed as fallback link
      Path expected = new Path(viewFsUri.toString(), "user1");
      assertEquals("Path did not match",
          expected, afterFallback.toArray(fallbackArray)[0]);

      // Create a directory using the returned fallback path and verify
      Path childDir = new Path(fallbackArray[0], "child");
      vfs.mkdirs(childDir);
      FileStatus status = fsTarget.getFileStatus(new Path(dir2, "child"));
      assertTrue(status.isDirectory());
      assertTrue(vfs.getFileStatus(childDir).isDirectory());
    }
  }
