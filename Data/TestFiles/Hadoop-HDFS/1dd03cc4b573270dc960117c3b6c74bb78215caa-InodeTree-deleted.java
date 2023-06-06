    final T targetFileSystem;   // file system object created from the link.
        final T targetFs, final URI aTargetDirLink) {
      targetFileSystem = targetFs;
    public T getTargetFileSystem() {
          getTargetFileSystem(new URI(target)), new URI(target));
  protected abstract T getTargetFileSystem(URI uri)
      throws UnsupportedFileSystemException, URISyntaxException, IOException;
          getTargetFileSystem(new URI(mergeSlashTarget)),
              getTargetFileSystem(new URI(le.getTarget())),
              new URI(le.getTarget()));
      rootFallbackLink =
          new INodeLink<T>(mountTableName, ugi, getTargetFileSystem(theUri),
              theUri);
   * @throws FileNotFoundException
      throws FileNotFoundException {
      T targetFs = getTargetFileSystem(
          new URI(targetOfResolvedPathStr));
    } catch (IOException ex) {
      LOGGER.error(String.format(
          "Got Exception while build resolve result."
              + " ResultKind:%s, resolvedPathStr:%s,"
              + " targetOfResolvedPathStr:%s, remainingPath:%s,"
              + " will return null.",
          resultKind, resolvedPathStr, targetOfResolvedPathStr, remainingPath),
          ex);
      return null;
