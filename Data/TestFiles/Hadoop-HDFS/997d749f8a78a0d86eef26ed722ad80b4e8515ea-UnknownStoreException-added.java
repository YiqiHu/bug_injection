import org.apache.hadoop.fs.PathIOException;
public class UnknownStoreException extends PathIOException {
   *
   * @param path    path trying to access.
   * @param message message.
  public UnknownStoreException(final String path, final String message) {
    this(path, message, null);
   *
   * @param path    path trying to access.
   * @param message message.
   * @param cause   cause (may be null).
  public UnknownStoreException(String path, final String message,
      Throwable cause) {
    super(path, message);
