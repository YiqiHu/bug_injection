  private static final int CUSTOM_READ_AHEAD_BUFFER_SIZE = 8 * CUSTOM_BLOCK_BUFFER_SIZE;
  private static final int THREAD_SLEEP_10_SECONDS = 10;
  private static final int TIMEOUT_30_SECONDS = 30000;
  @Test(timeout = TIMEOUT_30_SECONDS)
  public void testReadAheadCounters() throws IOException, InterruptedException {
      /*
       * Since, readAhead is done in background threads. Sometimes, the
       * threads aren't finished in the background and could result in
       * inaccurate results. So, we wait till we have the accurate values
       * with a limit of 30 seconds as that's when the test times out.
       *
       */
      while (stats.getRemoteBytesRead() < CUSTOM_READ_AHEAD_BUFFER_SIZE
          || stats.getReadAheadBytesRead() < CUSTOM_BLOCK_BUFFER_SIZE) {
        Thread.sleep(THREAD_SLEEP_10_SECONDS);
      }

       * blocks of 4KB which is equal to 8KB. But, sometimes to get more than
       * one block from readAhead buffer we might have to wait for background
       * would be faster. Therefore, readAheadBytesRead would be equal to or
       * greater than 4KB.
       * remotely could also be greater than 32Kb.
          .isGreaterThanOrEqualTo(CUSTOM_BLOCK_BUFFER_SIZE);
          .isGreaterThanOrEqualTo(CUSTOM_READ_AHEAD_BUFFER_SIZE);
