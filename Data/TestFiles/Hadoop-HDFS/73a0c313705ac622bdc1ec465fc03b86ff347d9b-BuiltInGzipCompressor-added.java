  private final byte[] gzipHeader = new byte[]{
  private final byte[] gzipTrailer = new byte[]{
  private final int gzipHeaderLen = gzipHeader.length;
  private final int gzipTrailerLen = gzipTrailer.length;
    int n = Math.min(len, gzipHeaderLen - headerOff);
    System.arraycopy(gzipHeader, headerOff, b, off, n);
    if (headerOff == gzipHeaderLen) {
      gzipTrailer[0] = (byte) (streamCrc & 0x000000ff);
      gzipTrailer[1] = (byte) ((streamCrc & 0x0000ff00) >> 8);
      gzipTrailer[2] = (byte) ((streamCrc & 0x00ff0000) >> 16);
      gzipTrailer[3] = (byte) ((streamCrc & 0xff000000) >> 24);
      gzipTrailer[4] = (byte) (accuBufLen & 0x000000ff);
      gzipTrailer[5] = (byte) ((accuBufLen & 0x0000ff00) >> 8);
      gzipTrailer[6] = (byte) ((accuBufLen & 0x00ff0000) >> 16);
      gzipTrailer[7] = (byte) ((accuBufLen & 0xff000000) >> 24);
    int n = Math.min(len, gzipTrailerLen - trailerOff);
    System.arraycopy(gzipTrailer, trailerOff, b, off, n);
    if (trailerOff == gzipTrailerLen) {
