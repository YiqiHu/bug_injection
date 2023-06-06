import java.util.Arrays;
  @Test
  public void testGetFileStatusInVersioningBucket() throws Exception {
    Path file = this.path("/test/hadoop/file");
    for (int i = 1; i <= 30; ++i) {
      this.createFile(new Path(file, "sub" + i));
    }
    assertTrue("File exists", this.fs.exists(file));
    FileStatus fs = this.fs.getFileStatus(file);
    assertEquals(fs.getOwner(),
        UserGroupInformation.getCurrentUser().getShortUserName());
    assertEquals(fs.getGroup(),
        UserGroupInformation.getCurrentUser().getShortUserName());

    AliyunOSSFileSystemStore store = ((AliyunOSSFileSystem)this.fs).getStore();
    for (int i = 0; i < 29; ++i) {
      store.deleteObjects(Arrays.asList("test/hadoop/file/sub" + i));
    }

    // HADOOP-16840, will throw FileNotFoundException without this fix
    this.fs.getFileStatus(file);
  }

