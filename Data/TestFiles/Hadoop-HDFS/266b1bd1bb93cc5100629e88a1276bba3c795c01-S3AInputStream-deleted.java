            onReadFailure(e, 1, true);
            onReadFailure(e, 1, false);
   * Handle an IOE on a read by attempting to re-open the stream.
   * @param length length of data being attempted to read
   * @throws IOException any exception thrown on the re-open attempt.
  private void onReadFailure(IOException ioe, int length, boolean forceAbort)
          throws IOException {
    reopen("failure recovery", pos, length, forceAbort);
            onReadFailure(e, len, true);
            onReadFailure(e, len, false);
