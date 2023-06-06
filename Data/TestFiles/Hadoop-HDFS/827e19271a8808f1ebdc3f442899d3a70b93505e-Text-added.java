    if (ensureCapacity(length + len)) {
      // Try to expand the backing array by the factor of 1.5x
      // (by taking the current size + diving it by half)
      int targetSize = Math.max(capacity, bytes.length + (bytes.length >> 1));
      bytes = new byte[targetSize];
