                  .getRootFallbackLink().getTargetFileSystem()).getMyFs() :
    try {
      return ((ChRootedFileSystem) fsState.getRootFallbackLink()
          .getTargetFileSystem()).getMyFs();
    } catch (IOException ex) {
      LOG.error("Could not get fallback filesystem ");
    }
    return null;
