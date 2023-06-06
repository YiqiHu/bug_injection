    int capacity = Math.max(length + len, length + (length >> 1));
    if (ensureCapacity(capacity)) {
      bytes = new byte[capacity];
