  /**
   * {@inheritDoc}
   *
   * If the given path is a symlink, the path will be resolved to a target path
   * and it will get the resolved path's FileStatus object. It will not be
   * represented as a symlink and isDirectory API returns true if the resolved
   * path is a directory, false otherwise.
   */
  /**
   * {@inheritDoc}
   *
   * If any of the the immediate children of the given path f is a symlink, the
   * returned FileStatus object of that children would be represented as a
   * symlink. It will not be resolved to the target path and will not get the
   * target path FileStatus object. The target path will be available via
   * getSymlink on that children's FileStatus object. Since it represents as
   * symlink, isDirectory on that children's FileStatus will return false.
   *
   * If you want to get the FileStatus of target path for that children, you may
   * want to use GetFileStatus API with that children's symlink path. Please see
   * {@link Hdfs#getFileStatus(Path f)}
   */
