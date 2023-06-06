  private static final byte[] GZIP_HEADER = new byte[]{
  private static final byte[] GZIP_TRAILER = new byte[]{
  private static final int GZIP_HEADER_LEN = GZIP_HEADER.length;
  private static final int GZIP_TRAILER_LEN = GZIP_TRAILER.length;
    int n = Math.min(len, GZIP_HEADER_LEN - headerOff);
    System.arraycopy(GZIP_HEADER, headerOff, b, off, n);
    if (headerOff == GZIP_HEADER_LEN) {
      GZIP_TRAILER[0] = (byte) (streamCrc & 0x000000ff);
      GZIP_TRAILER[1] = (byte) ((streamCrc & 0x0000ff00) >> 8);
      GZIP_TRAILER[2] = (byte) ((streamCrc & 0x00ff0000) >> 16);
      GZIP_TRAILER[3] = (byte) ((streamCrc & 0xff000000) >> 24);
      GZIP_TRAILER[4] = (byte) (accuBufLen & 0x000000ff);
      GZIP_TRAILER[5] = (byte) ((accuBufLen & 0x0000ff00) >> 8);
      GZIP_TRAILER[6] = (byte) ((accuBufLen & 0x00ff0000) >> 16);
      GZIP_TRAILER[7] = (byte) ((accuBufLen & 0xff000000) >> 24);
    int n = Math.min(len, GZIP_TRAILER_LEN - trailerOff);
    System.arraycopy(GZIP_TRAILER, trailerOff, b, off, n);
    if (trailerOff == GZIP_TRAILER_LEN) {
