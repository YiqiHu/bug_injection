        try {
          return dfs.createWrappedInputStream(dfsis);
        } catch (IOException ex){
          dfsis.close();
          throw ex;
        }
        return safelyCreateWrappedOutputStream(out);
        return safelyCreateWrappedOutputStream(dfsos);
        return safelyCreateWrappedOutputStream(out);
    return safelyCreateWrappedOutputStream(dfsos);
        return safelyCreateWrappedOutputStream(out);
        return safelyCreateWrappedOutputStream(dfsos);
  // Private helper to ensure the wrapped inner stream is closed safely
  // upon IOException throw during wrap.
  // Assuming the caller owns the inner stream which needs to be closed upon
  // wrap failure.
  private HdfsDataOutputStream safelyCreateWrappedOutputStream(
      DFSOutputStream dfsos) throws IOException {
    try {
      return dfs.createWrappedOutputStream(dfsos, statistics);
    } catch (IOException ex) {
      dfsos.close();
      throw ex;
    }
  }

