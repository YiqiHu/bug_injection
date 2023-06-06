    private boolean didInitialRead;
      if (b == null) {
        throw new NullPointerException();
      }
      if (off < 0 || len < 0 || len > b.length - off) {
        throw new IndexOutOfBoundsException();
      }
      if (len == 0) {
        return 0;
      }
      // When startingPos > 0, the stream should be initialized at the end of
      // one block (which would correspond to be the start of another block).
      // Thus, the initial read would technically be reading one byte passed a
      // BZip2 end of block marker. To be consistent, we should also be
      // updating the position to be one byte after the end of an block on the
      // initial read.
      boolean initializedAtEndOfBlock =
          !didInitialRead && startingPos > 0 && readMode == READ_MODE.BYBLOCK;
      int result = initializedAtEndOfBlock
          ? BZip2Constants.END_OF_BLOCK
          : this.input.read(b, off, len);
        result = this.input.read(b, off, 1);
      didInitialRead = true;
        didInitialRead = false;
