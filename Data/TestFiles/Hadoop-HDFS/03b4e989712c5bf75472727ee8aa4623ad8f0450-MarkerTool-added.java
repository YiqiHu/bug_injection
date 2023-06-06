    ScanResult result;
    try {
      result = execute(
              new ScanArgsBuilder()
                      .withSourceFS(fs)
                      .withPath(path)
                      .withDoPurge(clean)
                      .withMinMarkerCount(expectedMin)
                      .withMaxMarkerCount(expectedMax)
                      .withLimit(limit)
                      .withNonAuth(nonAuth)
                      .build());
    } catch (UnknownStoreException ex) {
      // bucket doesn't exist.
      // replace the stack trace with an error code.
      throw new ExitUtil.ExitException(EXIT_NOT_FOUND,
              ex.toString(), ex);
    }
