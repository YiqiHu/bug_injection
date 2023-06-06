import javax.annotation.Nullable;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FutureDataInputStreamBuilder;
import org.apache.hadoop.fs.PathIOException;

import static org.apache.hadoop.util.functional.FutureIO.awaitFuture;
   * @throws PathIOException JSON parse problem
   * @throws IOException IO problems
    return load(fs, path, null);
  }

  /**
   * Load from a Hadoop filesystem.
   * If a file status is supplied, it's passed in to the openFile()
   * call so that FS implementations can optimize their opening.
   * @param fs filesystem
   * @param path path
   * @param status status of the file to open.
   * @return a loaded object
   * @throws PathIOException JSON parse problem
   * @throws EOFException file status references an empty file
   * @throws IOException IO problems
   */
  public T load(FileSystem fs, Path path, @Nullable FileStatus status)
      throws IOException {

    if (status != null && status.getLen() == 0) {
      throw new EOFException("No data in " + path);
    }
    FutureDataInputStreamBuilder builder = fs.openFile(path);
    if (status != null) {
      builder.withFileStatus(status);
    }
    try (FSDataInputStream dataInputStream =
             awaitFuture(builder.build())) {
      throw new PathIOException(path.toString(),
          "Failed to read JSON file " + e, e);
