  public void setVerifyChecksum(final boolean verifyChecksum) {
    List<InodeTree.MountPoint<FileSystem>> mountPoints =
        fsState.getMountPoints();
    Map<String, FileSystem> fsMap = initializeMountedFileSystems(mountPoints);
    for (InodeTree.MountPoint<FileSystem> mount : mountPoints) {
      fsMap.get(mount.src).setVerifyChecksum(verifyChecksum);
    }
  public void setWriteChecksum(final boolean writeChecksum) {
    List<InodeTree.MountPoint<FileSystem>> mountPoints =
        fsState.getMountPoints();
    Map<String, FileSystem> fsMap = initializeMountedFileSystems(mountPoints);
    for (InodeTree.MountPoint<FileSystem> mount : mountPoints) {
      fsMap.get(mount.src).setWriteChecksum(writeChecksum);
    }
