    final InnerCache innerCache = new InnerCache(fsGetter);
        protected FileSystem getTargetFileSystem(final URI uri)
          throws URISyntaxException, IOException {
          FileSystem fs;
          if (enableInnerCache) {
            fs = innerCache.get(uri, config);
          } else {
            fs = fsGetter.get(uri, config);
          }
          return new ChRootedFileSystem(fs, uri);

    if (enableInnerCache) {
      // All fs instances are created and cached on startup. The cache is
      // readonly after the initialize() so the concurrent access of the cache
      // is safe.
      cache = innerCache;
    }
      res = fsState.resolve(getUriPath(f), true);
      mount.target.targetFileSystem.setVerifyChecksum(verifyChecksum);
      mount.target.targetFileSystem.setWriteChecksum(writeChecksum);
      FileSystem targetFs = mountPoint.target.targetFileSystem;
    if (fsState.isRootInternalDir() && fsState.getRootFallbackLink() != null) {
      children.addAll(Arrays.asList(
          fsState.getRootFallbackLink().targetFileSystem
              .getChildFileSystems()));
