  /**
   * Clean the staging folder created by distcp.
   */
  protected synchronized void cleanup() {
        synchronized (this) {
          if (jobFS != null) {
            jobFS.delete(metaFolder, true);
          }
          metaFolder = null;
