import org.apache.hadoop.classification.VisibleForTesting;
        writeHeader(out);
  @VisibleForTesting
  public static void writeHeader(OutputStream out) throws IOException {
    // The compressed bzip2 stream should start with the
    // identifying characters BZ. Caller of CBZip2OutputStream
    // i.e. this class must write these characters.
    out.write(HEADER.getBytes(StandardCharsets.UTF_8));
  }
