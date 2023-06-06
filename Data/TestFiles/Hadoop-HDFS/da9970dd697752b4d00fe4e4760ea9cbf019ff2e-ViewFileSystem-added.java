    private final String[] targetFileSystemPaths;
    MountPoint(Path srcPath, String[] targetFs) {
      targetFileSystemPaths = targetFs;
      URI[] targetUris = new URI[targetFileSystemPaths.length];
      for (int i = 0; i < targetFileSystemPaths.length; i++) {
        targetUris[i] = URI.create(targetFileSystemPaths[i]);
      }
      return targetUris;
    }

    public String[] getTargetFileSystemPaths() {
      return targetFileSystemPaths;
