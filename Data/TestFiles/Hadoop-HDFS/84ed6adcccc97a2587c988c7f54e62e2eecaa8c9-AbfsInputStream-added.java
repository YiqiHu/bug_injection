  private long bytesFromReadAhead; // bytes read from readAhead; for testing
  private long bytesFromRemoteRead; // bytes read remotely; for testing
      bytesFromReadAhead += receivedBytes;
    bytesFromRemoteRead += bytesRead;
  /**
   * Getter for bytes read from readAhead buffer that fills asynchronously.
   *
   * @return value of the counter in long.
   */
  @VisibleForTesting
  public long getBytesFromReadAhead() {
    return bytesFromReadAhead;
  }

  /**
   * Getter for bytes read remotely from the data store.
   *
   * @return value of the counter in long.
   */
  @VisibleForTesting
  public long getBytesFromRemoteRead() {
    return bytesFromRemoteRead;
  }

