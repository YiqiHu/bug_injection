import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.thirdparty.com.google.common.math.Stats;
  private static final double EPSILON = 1e-42;
   * sample count, the mean does not lose accuracy. This also validates that
   * the std dev is correct, assuming samples of equal value.
  @Test
  public void testMutableStatWithBulkAdd() {
    List<Long> samples = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      samples.add(1000L);
    }
    for (int i = 0; i < 1000; i++) {
      samples.add(2000L);
    }
    Stats stats = Stats.of(samples);

    for (int bulkSize : new int[] {1, 10, 100, 1000}) {
      MetricsRecordBuilder rb = mockMetricsRecordBuilder();
      MetricsRegistry registry = new MetricsRegistry("test");
      MutableStat stat = registry.newStat("Test", "Test", "Ops", "Val", true);

      for (int i = 0; i < samples.size(); i += bulkSize) {
        stat.add(bulkSize, samples
            .subList(i, i + bulkSize)
            .stream()
            .mapToLong(Long::longValue)
            .sum()
        );
      }
      registry.snapshot(rb, false);

      assertCounter("TestNumOps", 2000L, rb);
      assertGauge("TestAvgVal", stats.mean(), rb);
      assertGauge("TestStdevVal", stats.sampleStandardDeviation(), rb);
    }
  }

  @Test
  public void testLargeMutableStatAdd() {
    MutableStat stat = registry.newStat("Test", "Test", "Ops", "Val", true);
    long sample = 1000000000000009L;
    for (int i = 0; i < 100; i++) {
      stat.add(1, sample);
    }
    assertCounter("TestNumOps", 100L, rb);
    assertGauge("TestAvgVal", (double) sample, rb);
    assertGauge("TestStdevVal", 0.0, rb);
