import org.apache.hadoop.classification.VisibleForTesting;
      skipResult = skipToNextBlockMarker();
  /**
   * Skips bytes in the stream until the start marker of a block is reached
   * or end of stream is reached. Used for testing purposes to identify the
   * start offsets of blocks.
   */
  @VisibleForTesting
  boolean skipToNextBlockMarker() throws IOException {
    return skipToNextMarker(
        CBZip2InputStream.BLOCK_DELIMITER, DELIMITER_BIT_LENGTH);
  }

      skipResult = skipToNextBlockMarker();
