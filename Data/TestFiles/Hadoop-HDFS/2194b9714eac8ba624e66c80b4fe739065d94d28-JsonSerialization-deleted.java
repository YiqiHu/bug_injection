   * There's a check for data availability after the file is open, by
   * raising an EOFException if stream.available == 0.
   * This allows for a meaningful exception without the round trip overhead
   * of a getFileStatus call before opening the file. It may be brittle
   * against an FS stream which doesn't return a value here, but the
   * standard filesystems all do.
   * JSON parsing and mapping problems
   * are converted to IOEs.
   * @throws IOException IO or JSON parse problems
    try (FSDataInputStream dataInputStream = fs.open(path)) {
      // throw an EOF exception if there is no data available.
      if (dataInputStream.available() == 0) {
        throw new EOFException("No data in " + path);
      }
      throw new IOException(
          String.format("Failed to read JSON file \"%s\": %s", path, e),
          e);
