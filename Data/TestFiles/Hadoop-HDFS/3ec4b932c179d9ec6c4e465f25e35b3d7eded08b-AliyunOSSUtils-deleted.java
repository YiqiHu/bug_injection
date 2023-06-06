  private static LocalDirAllocator directoryAllocator;
   *  @param path prefix for the temporary file
   *  @param size the size of the file that is going to be written
   *  @param conf the Configuration object
   *  @return a unique temporary file
   *  @throws IOException IO problems
  public static File createTmpFileForWrite(String path, long size,
      directoryAllocator = new LocalDirAllocator(BUFFER_DIR_KEY);
    return directoryAllocator.createTmpFileForWrite(path, size, conf);
