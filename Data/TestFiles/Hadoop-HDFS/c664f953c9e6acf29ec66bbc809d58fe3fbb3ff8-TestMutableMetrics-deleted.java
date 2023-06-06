  private final double EPSILON = 1e-42;
   * sample count, the mean does not lose accuracy.
  @Test public void testMutableStatWithBulkAdd() {
    MutableStat stat = registry.newStat("Test", "Test", "Ops", "Val", false);
    stat.add(1000, 1000);
    stat.add(1000, 2000);
    assertCounter("TestNumOps", 2000L, rb);
    assertGauge("TestAvgVal", 1.5, rb);
