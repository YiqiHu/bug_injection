    boolean alreadyReadAfterSplit = didReadAfterSplit();

    boolean justReadAfterSplit = !alreadyReadAfterSplit && didReadAfterSplit();

    if (justReadAfterSplit && inDelimiter && bytesRead > 0) {
      if (didReadAfterSplit()) {

  private boolean didReadAfterSplit() throws IOException {
    return scin.getPos() > scin.getAdjustedEnd();
  }
