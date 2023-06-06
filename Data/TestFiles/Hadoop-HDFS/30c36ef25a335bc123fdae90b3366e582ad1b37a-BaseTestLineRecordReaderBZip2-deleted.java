      for (int i = 0; i < numBlocks; i++) {
        long start = i == 0 ? 0 : nextBlockOffsets.get(i - 1);
        long end = i == numBlocks - 1 ? fileSize : nextBlockOffsets.get(i);
        long length = end - start;

        String message = "At i=" + i;
        long expectedCount = countsIfSplitAtBlocks[i];
        assertEquals(
            message, expectedCount, reader.countRecords(start, length));
      }
