    @Override
    public ContentSummary getContentSummary(Path f) throws IOException {
      long[] summary = {0, 0, 1};
      for (FileStatus status : listStatus(f)) {
        Path targetPath =
            Path.getPathWithoutSchemeAndAuthority(status.getPath());
        InodeTree.ResolveResult<FileSystem> res =
            fsState.resolve(targetPath.toString(), true);
        ContentSummary child =
            res.targetFileSystem.getContentSummary(res.remainingPath);
        summary[0] += child.getLength();
        summary[1] += child.getFileCount();
        summary[2] += child.getDirectoryCount();
      }
      return new ContentSummary.Builder()
          .length(summary[0])
          .fileCount(summary[1])
          .directoryCount(summary[2])
          .build();
    }

    @Override
    public FsStatus getStatus(Path p) throws IOException {
      long[] summary = {0, 0, 0};
      for (FileStatus status : listStatus(p)) {
        Path targetPath =
            Path.getPathWithoutSchemeAndAuthority(status.getPath());
        InodeTree.ResolveResult<FileSystem> res =
            fsState.resolve(targetPath.toString(), true);
        FsStatus child = res.targetFileSystem.getStatus(res.remainingPath);
        summary[0] += child.getCapacity();
        summary[1] += child.getUsed();
        summary[2] += child.getRemaining();
      }
      return new FsStatus(summary[0], summary[1], summary[2]);
    }

