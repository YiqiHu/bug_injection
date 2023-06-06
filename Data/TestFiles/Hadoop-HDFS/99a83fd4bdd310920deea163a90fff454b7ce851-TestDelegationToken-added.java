import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
  @Test
  public void testMultipleDelegationTokenSecretManagerMetrics() {
    TestDelegationTokenSecretManager dtSecretManager1 =
        new TestDelegationTokenSecretManager(0, 0, 0, 0);
    assertNotNull(dtSecretManager1.getMetrics());

    TestDelegationTokenSecretManager dtSecretManager2 =
        new TestDelegationTokenSecretManager(0, 0, 0, 0);
    assertNotNull(dtSecretManager2.getMetrics());

    DefaultMetricsSystem.instance().init("test");

    TestDelegationTokenSecretManager dtSecretManager3 =
        new TestDelegationTokenSecretManager(0, 0, 0, 0);
    assertNotNull(dtSecretManager3.getMetrics());
  }

          () -> generateDelegationToken(dtSecretManager, "SomeUser", "JobTracker"));
          "updateToken", () -> dtSecretManager.renewToken(token, "JobTracker"));
          "removeToken", () -> dtSecretManager.cancelToken(token, "JobTracker"));
      callAndValidateFailureMetrics(dtSecretManager, "storeToken", false,
      callAndValidateFailureMetrics(dtSecretManager, "updateToken", true,
      callAndValidateFailureMetrics(dtSecretManager, "removeToken", true,
      MutableRate metric, String statName,  Callable<T> callable)
    long metricBefore = metric.lastStat().numSamples();
    long statBefore = stat.getSamples();
    assertEquals(metricBefore + 1, metric.lastStat().numSamples());
    assertEquals(statBefore + 1, stat.getSamples());
      String statName, boolean expectError, int errorSleepMillis, Callable<T> callable)
      throws Exception {
    long counterBefore = counter.value();
    long statBefore = failureStat.getSamples();
    assertEquals(counterBefore + 1, counter.value());
    assertEquals(statBefore + 1, failureStat.getSamples());
