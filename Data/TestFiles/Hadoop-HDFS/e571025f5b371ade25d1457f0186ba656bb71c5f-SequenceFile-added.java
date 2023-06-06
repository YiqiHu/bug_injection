import org.apache.hadoop.fs.StreamCapabilities;
  public static class Writer implements java.io.Closeable, Syncable,
                  Flushable, StreamCapabilities {

    @Override
    public void flush() throws IOException {
      if (out != null) {
        out.flush();
      }
    }

    @Override
    public boolean hasCapability(String capability) {
      if (out !=null && capability != null) {
        return out.hasCapability(capability);
      }
      return false;
    }
