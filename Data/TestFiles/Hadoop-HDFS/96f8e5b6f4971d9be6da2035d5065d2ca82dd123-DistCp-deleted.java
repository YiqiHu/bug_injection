  private synchronized void cleanup() {
        if (jobFS != null) {
          jobFS.delete(metaFolder, true);
        metaFolder = null;
