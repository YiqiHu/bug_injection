          // When exception happens before re-setting wrappedStream in "reopen" called
          // by onReadFailure, then wrappedStream will be null. But the **retry** may
          // re-execute this block and cause NPE if we don't check wrappedStream
          if (wrappedStream == null) {
            reopen("failure recovery", getPos(), 1, false);
          }
            onReadFailure(e, true);
            onReadFailure(e, false);
   * Close the stream on read failure.
  private void onReadFailure(IOException ioe, boolean forceAbort) {
    closeStream("failure recovery", contentRangeFinish, forceAbort);
          // When exception happens before re-setting wrappedStream in "reopen" called
          // by onReadFailure, then wrappedStream will be null. But the **retry** may
          // re-execute this block and cause NPE if we don't check wrappedStream
          if (wrappedStream == null) {
            reopen("failure recovery", getPos(), 1, false);
          }
            onReadFailure(e, true);
            onReadFailure(e, false);
