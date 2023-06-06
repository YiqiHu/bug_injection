
  /**
   * Tests if fullyDelete deletes symlink's content when deleting unremovable dir symlink.
   * @throws IOException
   */
  @Test (timeout = 30000)
  public void testFailFullyDeleteDirSymlinks() throws IOException {
    File linkDir = new File(del, "tmpDir");
    FileUtil.setWritable(del, false);
    // Since tmpDir is symlink to tmp, fullyDelete(tmpDir) should not
    // delete contents of tmp. See setupDirs for details.
    boolean ret = FileUtil.fullyDelete(linkDir);
    // fail symlink deletion
    Assert.assertFalse(ret);
    Assert.assertTrue(linkDir.exists());
    Assert.assertEquals(5, del.list().length);
    // tmp dir should exist
    validateTmpDir();
    // simulate disk recovers and turns good
    FileUtil.setWritable(del, true);
    ret = FileUtil.fullyDelete(linkDir);
    // success symlink deletion
    Assert.assertTrue(ret);
    Assert.assertFalse(linkDir.exists());
    Assert.assertEquals(4, del.list().length);
    // tmp dir should exist
    validateTmpDir();
  }

