import java.util.function.Function;
    private T targetFileSystem;   // file system object created from the link.
    // Function to initialize file system. Only applicable for simple links
    private Function<URI, T> fileSystemInitMethod;
    private final Object lock = new Object();
        Function<URI, T> createFileSystemMethod,
        final URI aTargetDirLink) {
      targetFileSystem = null;
      this.fileSystemInitMethod = createFileSystemMethod;
    /**
     * Get the instance of FileSystem to use, creating one if needed.
     * @return An Initialized instance of T
     * @throws IOException
     */
    public T getTargetFileSystem() throws IOException {
      if (targetFileSystem != null) {
        return targetFileSystem;
      }
      // For non NFLY and MERGE links, we initialize the FileSystem when the
      // corresponding mount path is accessed.
      if (targetDirLinkList.length == 1) {
        synchronized (lock) {
          if (targetFileSystem != null) {
            return targetFileSystem;
          }
          targetFileSystem = fileSystemInitMethod.apply(targetDirLinkList[0]);
          if (targetFileSystem == null) {
            throw new IOException(
                "Could not initialize target File System for URI : " +
                    targetDirLinkList[0]);
          }
        }
      }
          initAndGetTargetFs(), new URI(target));
  protected abstract Function<URI, T> initAndGetTargetFs();
          initAndGetTargetFs(),
              initAndGetTargetFs(), new URI(le.getTarget()));
      rootFallbackLink = new INodeLink<T>(mountTableName, ugi,
          initAndGetTargetFs(), theUri);
   * @throws IOException
      throws IOException {
      T targetFs = initAndGetTargetFs()
          .apply(new URI(targetOfResolvedPathStr));
      if (targetFs == null) {
        LOGGER.error(String.format(
            "Not able to initialize target file system."
                + " ResultKind:%s, resolvedPathStr:%s,"
                + " targetOfResolvedPathStr:%s, remainingPath:%s,"
                + " will return null.",
            resultKind, resolvedPathStr, targetOfResolvedPathStr,
            remainingPath));
        return null;
      }
