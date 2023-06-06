import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.PathIOException;
  /**
   * round trip through both load APIs.
   */
      assertEquals("JSON loaded with load(fs, path)",
          source,
          serDeser.load(fs, tempPath));
      assertEquals("JSON loaded with load(fs, path, status)",
          source,
          serDeser.load(fs, tempPath, fs.getFileStatus(tempPath)));
  /**
   * 0 byte file through the load(path) API will fail with a wrapped
   * Parser exception.
   * 0 byte file through the load(path, status) API will fail with a wrapped
   * Parser exception.
   */
      LambdaTestUtils.intercept(PathIOException.class,
  /**
   * 0 byte file through the load(path, status) API will fail with an
   * EOFException.
   */
  @Test
  public void testFileSystemEmptyStatus() throws Throwable {
    File tempFile = File.createTempFile("Keyval", ".json");
    Path tempPath = new Path(tempFile.toURI());
    LocalFileSystem fs = FileSystem.getLocal(new Configuration());
    try {
      final FileStatus st = fs.getFileStatus(tempPath);
      LambdaTestUtils.intercept(EOFException.class,
          () -> serDeser.load(fs, tempPath, st));
    } finally {
      fs.delete(tempPath, false);
    }
  }

