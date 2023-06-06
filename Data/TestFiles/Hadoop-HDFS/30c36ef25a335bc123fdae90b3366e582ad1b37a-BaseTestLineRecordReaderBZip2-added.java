import java.util.ArrayList;
import java.util.StringJoiner;
import static org.apache.hadoop.util.Preconditions.checkArgument;
    countAssert.assertSplittingEachBlockRangeInThreeParts();
    countAssert.assertSplitsAroundBlockStartOffsets();
      assertSplits(getSplitsAtBlocks());

    private void assertSplittingEachBlockRangeInThreeParts()
        throws IOException {
      for (SplitRange splitRange : getSplitsAtBlocks()) {
        long[] expectedNumRecordsPerPart = new long[] {
            splitRange.expectedNumRecords, 0, 0
        };
        List<SplitRange> parts = splitRange.divide(expectedNumRecordsPerPart);
        assertSplits(parts);
      }
    }

    private void assertSplitsAroundBlockStartOffsets()
        throws IOException {
      for (SplitRange split : getSplitsAtBlocks()) {
        assertSplit(split.withLength(1));
        if (split.start > 0) {
          assertSplit(split.moveBy(-2).withLength(3));
          assertSplit(split.moveBy(-2).withLength(2).withExpectedNumRecords(0));
          assertSplit(split.moveBy(-1).withLength(2));
          assertSplit(split.moveBy(-1).withLength(1).withExpectedNumRecords(0));
        }
        assertSplit(split.moveBy(1).withLength(1).withExpectedNumRecords(0));
        assertSplit(split.moveBy(2).withLength(1).withExpectedNumRecords(0));
      }
    }

    private List<SplitRange> getSplitsAtBlocks() {
      List<SplitRange> splits = new ArrayList<>();
      for (int i = 0; i < numBlocks; i++) {
        String name = "Block" + i;
        long start = i == 0 ? 0 : nextBlockOffsets.get(i - 1);
        long end = i == numBlocks - 1 ? fileSize : nextBlockOffsets.get(i);
        long length = end - start;
        long expectedNumRecords = countsIfSplitAtBlocks[i];
        splits.add(new SplitRange(name, start, length, expectedNumRecords));
      }
      return splits;
    }

    private void assertSplits(Iterable<SplitRange> splitRanges)
        throws IOException {
      for (SplitRange splitRange : splitRanges) {
        assertSplit(splitRange);
      }
    }

    private void assertSplit(SplitRange splitRange) throws IOException {
      String message = splitRange.toString();
      long actual = reader.countRecords(splitRange.start, splitRange.length);
      assertEquals(message, splitRange.expectedNumRecords, actual);
    }
  }

  private static class SplitRange {
    final private String name;
    final private long start;
    final private long length;
    final private long expectedNumRecords;

    SplitRange(
        String name,
        long start,
        long length,
        long expectedNumRecords) {
      this.name = name;
      this.start = start;
      this.length = length;
      this.expectedNumRecords = expectedNumRecords;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", SplitRange.class.getSimpleName() + "[", "]")
          .add("name='" + name + "'")
          .add("start=" + start)
          .add("length=" + length)
          .add("expectedNumRecords=" + expectedNumRecords)
          .toString();
    }

    List<SplitRange> divide(long[] expectedNumRecordsPerPart) {
      int numParts = expectedNumRecordsPerPart.length;
      checkArgument(numParts > 0);

      long minPartSize = length / numParts;
      checkArgument(minPartSize > 0);
      long lastPartExtraSize = length % numParts;

      List<SplitRange> partRanges = new ArrayList<>();
      long partStart = start;
      for (int i = 0; i < numParts; i++) {
        String partName = name + "_Part" + i;

        long extraSize = i == numParts - 1 ? lastPartExtraSize : 0;
        long partSize = minPartSize + extraSize;

        long partExpectedNumRecords = expectedNumRecordsPerPart[i];

        partRanges.add(new SplitRange(
            partName, partStart, partSize, partExpectedNumRecords));
        partStart += partSize;
      }
      return partRanges;
    }

    SplitRange withLength(long newLength) {
      return new SplitRange(name, start, newLength, expectedNumRecords);
    }

    SplitRange withExpectedNumRecords(long newExpectedNumRecords) {
      return new SplitRange(name, start, length, newExpectedNumRecords);
    }

    SplitRange moveBy(long delta) {
      return new SplitRange(name, start + delta, length, expectedNumRecords);
    }
