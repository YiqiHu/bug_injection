    /**
     * {@inheritDoc}
     *
     * Note: listStatus on root("/") considers listing from fallbackLink if
     * available. If the same directory name is present in configured mount
     * path as well as in fallback link, then only the configured mount path
     * will be listed in the returned result.
     */
      FileStatus[] fallbackStatuses = listStatusForFallbackLink();
      if (fallbackStatuses.length > 0) {
        return consolidateFileStatuses(fallbackStatuses, result);
      } else {
        return result;
      }
    }

    private FileStatus[] consolidateFileStatuses(FileStatus[] fallbackStatuses,
        FileStatus[] mountPointStatuses) {
      ArrayList<FileStatus> result = new ArrayList<>();
      Set<String> pathSet = new HashSet<>();
      for (FileStatus status : mountPointStatuses) {
        result.add(status);
        pathSet.add(status.getPath().getName());
      }
      for (FileStatus status : fallbackStatuses) {
        if (!pathSet.contains(status.getPath().getName())) {
          result.add(status);
        }
      }
      return result.toArray(new FileStatus[0]);
    }

    private FileStatus[] listStatusForFallbackLink() throws IOException {
      if (theInternalDir.isRoot() &&
          theInternalDir.getFallbackLink() != null) {
        FileSystem linkedFs =
            theInternalDir.getFallbackLink().getTargetFileSystem();
        // Fallback link is only applicable for root
        FileStatus[] statuses = linkedFs.listStatus(new Path("/"));
        for (FileStatus status : statuses) {
          // Fix the path back to viewfs scheme
          status.setPath(
              new Path(myUri.toString(), status.getPath().getName()));
        }
        return statuses;
      } else {
        return new FileStatus[0];
      }
