   * Returns the parent of a path or null if at root.
        (lastSlash == start && path.length() == start+1)) { // at root
    if (lastSlash==-1) {
      parent = path.substring(0, lastSlash==start?start+1:lastSlash);
