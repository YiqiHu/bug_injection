import java.io.IOException;

public class UnknownStoreException extends IOException {
   * @param message message
  public UnknownStoreException(final String message) {
    this(message, null);
   * @param message message
   * @param cause cause (may be null)
  public UnknownStoreException(final String message, Throwable cause) {
    super(message);
