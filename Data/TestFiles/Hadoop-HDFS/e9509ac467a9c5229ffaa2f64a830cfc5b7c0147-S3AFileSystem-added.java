import java.util.Iterator;
import java.util.TreeSet;
  /**
   * A cache of files that should be deleted when the FileSystem is closed
   * or the JVM is exited.
   */
  private final Set<Path> deleteOnExit = new TreeSet<>();

    return deleteWithoutCloseCheck(f, recursive);
  }

  /**
   * Same as delete(), except that it does not check if fs is closed.
   *
   * @param f the path to delete.
   * @param recursive if path is a directory and set to
   * true, the directory is deleted else throws an exception. In
   * case of a file the recursive can be set to either true or false.
   * @return true if the path existed and then was deleted; false if there
   * was no path in the first place, or the corner cases of root path deletion
   * have surfaced.
   * @throws IOException due to inability to delete a directory or file.
   */

  @VisibleForTesting
  protected boolean deleteWithoutCloseCheck(Path f, boolean recursive) throws IOException {
  /**
   * This override bypasses checking for existence.
   *
   * @param f the path to delete; this may be unqualified.
   * @return true, always.   * @param f the path to delete.
   * @return  true if deleteOnExit is successful, otherwise false.
   * @throws IOException IO failure
   */
  @Override
  public boolean deleteOnExit(Path f) throws IOException {
    Path qualifedPath = makeQualified(f);
    synchronized (deleteOnExit) {
      deleteOnExit.add(qualifedPath);
    }
    return true;
  }

  /**
   * Cancel the scheduled deletion of the path when the FileSystem is closed.
   * @param f the path to cancel deletion
   * @return true if the path was found in the delete-on-exit list.
   */
  @Override
  public boolean cancelDeleteOnExit(Path f) {
    Path qualifedPath = makeQualified(f);
    synchronized (deleteOnExit) {
      return deleteOnExit.remove(qualifedPath);
    }
  }

  /**
   * Delete all paths that were marked as delete-on-exit. This recursively
   * deletes all files and directories in the specified paths. It does not
   * check if file exists and filesystem is closed.
   *
   * The time to process this operation is {@code O(paths)}, with the actual
   * time dependent on the time for existence and deletion operations to
   * complete, successfully or not.
   */
  @Override
  protected void processDeleteOnExit() {
    synchronized (deleteOnExit) {
      for (Iterator<Path> iter = deleteOnExit.iterator(); iter.hasNext();) {
        Path path = iter.next();
        try {
          deleteWithoutCloseCheck(path, true);
        } catch (IOException e) {
          LOG.info("Ignoring failure to deleteOnExit for path {}", path);
          LOG.debug("The exception for deleteOnExit is {}", e);
        }
        iter.remove();
      }
    }
  }

