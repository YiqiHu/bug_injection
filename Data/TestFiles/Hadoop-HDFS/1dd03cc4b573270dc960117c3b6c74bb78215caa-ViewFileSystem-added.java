import java.util.function.Function;
import java.security.PrivilegedExceptionAction;
    cache = new InnerCache(fsGetter);
        protected Function<URI, FileSystem> initAndGetTargetFs() {
          return new Function<URI, FileSystem>() {
            @Override
            public FileSystem apply(final URI uri) {
              FileSystem fs;
              try {
                fs = ugi.doAs(new PrivilegedExceptionAction<FileSystem>() {
                  @Override
                  public FileSystem run() throws IOException {
                    if (enableInnerCache) {
                      synchronized (cache) {
                        return cache.get(uri, config);
                      }
                    } else {
                      return fsGetter().get(uri, config);
                    }
                  }
                });
                return new ChRootedFileSystem(fs, uri);
              } catch (IOException | InterruptedException ex) {
                LOG.error("Could not initialize the underlying FileSystem "
                    + "object. Exception: " + ex.toString());
              }
              return null;
            }
          };
    res = fsState.resolve(getUriPath(f), true);
    Map<String, FileSystem> fsMap = initializeMountedFileSystems(mountPoints);
      fsMap.get(mount.src).setVerifyChecksum(verifyChecksum);

  /**
   * Initialize the target filesystem for all mount points.
   * @param mountPoints The mount points
   * @return Mapping of mount point and the initialized target filesystems
   * @throws RuntimeException when the target file system cannot be initialized
   */
  private Map<String, FileSystem> initializeMountedFileSystems(
      List<InodeTree.MountPoint<FileSystem>> mountPoints) {
    FileSystem fs = null;
    Map<String, FileSystem> fsMap = new HashMap<>(mountPoints.size());
    for (InodeTree.MountPoint<FileSystem> mount : mountPoints) {
      try {
        fs = mount.target.getTargetFileSystem();
        fsMap.put(mount.src, fs);
      } catch (IOException ex) {
        String errMsg = "Not able to initialize FileSystem for mount path " +
            mount.src + " with exception " + ex;
        LOG.error(errMsg);
        throw new RuntimeException(errMsg, ex);
      }
    }
    return fsMap;
  }
    } catch (IOException e) {
      throw new RuntimeException("Not able to initialize fs in "
          + " getDefaultBlockSize for path " + f + " with exception", e);
    } catch (IOException e) {
      throw new RuntimeException("Not able to initialize fs in "
          + " getDefaultReplication for path " + f + " with exception", e);
    Map<String, FileSystem> fsMap = initializeMountedFileSystems(mountPoints);
      fsMap.get(mount.src).setWriteChecksum(writeChecksum);
    Map<String, FileSystem> fsMap = initializeMountedFileSystems(mountPoints);
      FileSystem targetFs = fsMap.get(mountPoint.src);
    try {
      if (fsState.isRootInternalDir() &&
          fsState.getRootFallbackLink() != null) {
        children.addAll(Arrays.asList(
            fsState.getRootFallbackLink().getTargetFileSystem()
                .getChildFileSystems()));
      }
    } catch (IOException ex) {
      LOG.error("Could not add child filesystems for source path "
          + fsState.getRootFallbackLink().fullPath + " with exception " + ex);
