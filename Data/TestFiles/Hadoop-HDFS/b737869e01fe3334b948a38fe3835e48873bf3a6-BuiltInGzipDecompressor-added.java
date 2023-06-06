import org.apache.hadoop.io.compress.AlreadyClosedException;
    FINISHED,
    /**
     * Immediately after end() has been called.
     */
    ENDED;
    if (state == GzipStateLabel.ENDED) {
      throw new AlreadyClosedException("decompress called on closed decompressor");
    }


    state = GzipStateLabel.ENDED;
