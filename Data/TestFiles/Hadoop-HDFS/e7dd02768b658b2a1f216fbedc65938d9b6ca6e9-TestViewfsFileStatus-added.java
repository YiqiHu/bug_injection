import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.After;
import org.junit.Before;
  @Before
  public void setUp() {
    FileUtil.fullyDelete(TEST_DIR);
    assertTrue(TEST_DIR.mkdirs());
  }

  @After
  public void tearDown() throws IOException {
    FileUtil.fullyDelete(TEST_DIR);
  }

    try (FileOutputStream fos =  new FileOutputStream(infile)) {
    try (FileSystem vfs = FileSystem.get(FsConstants.VIEWFS_URI, conf)) {
      assertEquals(ViewFileSystem.class, vfs.getClass());
      Path path = new Path("/foo/bar/baz", testfilename);
      FileStatus stat = vfs.getFileStatus(path);
      assertEquals(content.length, stat.getLen());
      ContractTestUtils.assertNotErasureCoded(vfs, path);
      assertTrue(path + " should have erasure coding unset in " +
          "FileStatus#toString(): " + stat,
          stat.toString().contains("isErasureCoded=false"));

      // check serialization/deserialization
      DataOutputBuffer dob = new DataOutputBuffer();
      stat.write(dob);
      DataInputBuffer dib = new DataInputBuffer();
      dib.reset(dob.getData(), 0, dob.getLength());
      FileStatus deSer = new FileStatus();
      deSer.readFields(dib);
      assertEquals(content.length, deSer.getLen());
      assertFalse(deSer.isErasureCoded());
    }
  }

  /**
   * Tests the ACL returned from getFileStatus for directories and files.
   * @throws IOException
   */
  @Test
  public void testListStatusACL() throws IOException {
    String testfilename = "testFileACL";
    String childDirectoryName = "testDirectoryACL";
    TEST_DIR.mkdirs();
    File infile = new File(TEST_DIR, testfilename);
    final byte[] content = "dingos".getBytes();

    try (FileOutputStream fos =  new FileOutputStream(infile)) {
      fos.write(content);
    }
    assertEquals(content.length, infile.length());
    File childDir = new File(TEST_DIR, childDirectoryName);
    childDir.mkdirs();

    Configuration conf = new Configuration();
    ConfigUtil.addLink(conf, "/file", infile.toURI());
    ConfigUtil.addLink(conf, "/dir", childDir.toURI());

    try (FileSystem vfs = FileSystem.get(FsConstants.VIEWFS_URI, conf)) {
      assertEquals(ViewFileSystem.class, vfs.getClass());
      FileStatus[] statuses = vfs.listStatus(new Path("/"));

      FileSystem localFs = FileSystem.getLocal(conf);
      FileStatus fileStat = localFs.getFileStatus(new Path(infile.getPath()));
      FileStatus dirStat = localFs.getFileStatus(new Path(childDir.getPath()));

      for (FileStatus status : statuses) {
        if (status.getPath().getName().equals("file")) {
          assertEquals(fileStat.getPermission(), status.getPermission());
        } else {
          assertEquals(dirStat.getPermission(), status.getPermission());
        }
      }

      localFs.setPermission(new Path(infile.getPath()),
          FsPermission.valueOf("-rwxr--r--"));
      localFs.setPermission(new Path(childDir.getPath()),
          FsPermission.valueOf("-r--rwxr--"));

      statuses = vfs.listStatus(new Path("/"));
      for (FileStatus status : statuses) {
        if (status.getPath().getName().equals("file")) {
          assertEquals(FsPermission.valueOf("-rwxr--r--"),
              status.getPermission());
        } else {
          assertEquals(FsPermission.valueOf("-r--rwxr--"),
              status.getPermission());
        }
      }
    }
