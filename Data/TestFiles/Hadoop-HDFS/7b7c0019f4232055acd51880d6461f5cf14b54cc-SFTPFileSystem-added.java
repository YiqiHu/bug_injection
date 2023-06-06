import java.util.concurrent.atomic.AtomicBoolean;
  private final AtomicBoolean closed = new AtomicBoolean(false);
  public static final String E_FS_CLOSED = "FileSystem is closed!";
    checkNotClosed();
    Configuration conf = getConf();
  @Override
  public void close() throws IOException {
    if (closed.getAndSet(true)) {
      return;
    }
    try {
      super.close();
    } finally {
      if (connectionPool != null) {
        connectionPool.shutdown();
      }
    }
  }

  /**
   * Verify that the input stream is open. Non blocking; this gives
   * the last state of the volatile {@link #closed} field.
   * @throws IOException if the connection is closed.
   */
  private void checkNotClosed() throws IOException {
    if (closed.get()) {
      throw new IOException(uri + ": " + E_FS_CLOSED);
    }
  }

