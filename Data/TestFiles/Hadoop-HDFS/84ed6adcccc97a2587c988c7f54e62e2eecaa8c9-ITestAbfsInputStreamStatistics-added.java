  @Test
  public void testReadAheadCounters() throws IOException {
       * blocks of 4KB which is equal to 8KB. But, sometimes to get blocks
       * from readAhead buffer we might have to wait for background
       * would be faster. Therefore, readAheadBytesRead would be greater than
       * or equal to the value of bytesFromReadAhead at the point we measure it.
       * remotely would be greater than or equal to the bytesFromRemoteRead
       * value that we measure at some point of the operation.
          .isGreaterThanOrEqualTo(in.getBytesFromReadAhead());
          .isGreaterThanOrEqualTo(in.getBytesFromRemoteRead());
