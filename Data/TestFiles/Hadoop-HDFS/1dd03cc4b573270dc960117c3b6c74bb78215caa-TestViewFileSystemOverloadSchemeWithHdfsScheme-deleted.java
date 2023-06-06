import org.apache.hadoop.fs.UnsupportedFileSystemException;
        FileSystem.get(conf);
  @Test(expected = UnsupportedFileSystemException.class, timeout = 30000)
