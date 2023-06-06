    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(infile);
    } finally {
      if (fos != null) {
        fos.close();
      }
    FileSystem vfs = FileSystem.get(FsConstants.VIEWFS_URI, conf);
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
