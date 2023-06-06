import org.apache.hadoop.fs.Path;
  private static volatile LocalDirAllocator directoryAllocator;
   * This does not mark the file for deletion when a process exits.
   * {@link LocalDirAllocator#createTmpFileForWrite(
   * String, long, Configuration)}.
   * @param pathStr prefix for the temporary file
   * @param size the size of the file that is going to be written
   * @param conf the Configuration object
   * @return a unique temporary file
   * @throws IOException IO problems
  public static File createTmpFileForWrite(String pathStr, long size,
      synchronized (AliyunOSSUtils.class) {
        if (directoryAllocator == null) {
          directoryAllocator = new LocalDirAllocator(BUFFER_DIR_KEY);
        }
      }
    Path path = directoryAllocator.getLocalPathForWrite(pathStr,
        size, conf);
    File dir = new File(path.getParent().toUri().getPath());
    String prefix = path.getName();
    // create a temp file on this directory
    return File.createTempFile(prefix, null, dir);
