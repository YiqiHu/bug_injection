          () -> generateDelegationToken(dtSecretManager, "SomeUser", "JobTracker"), 1);
          "updateToken", () -> dtSecretManager.renewToken(token, "JobTracker"), 1);
          "removeToken", () -> dtSecretManager.cancelToken(token, "JobTracker"), 1);
      callAndValidateFailureMetrics(dtSecretManager, "storeToken", 1, 1, false,
      callAndValidateFailureMetrics(dtSecretManager, "updateToken", 1, 2, true,
      callAndValidateFailureMetrics(dtSecretManager, "removeToken", 1, 3, true,
      MutableRate metric, String statName,  Callable<T> callable, int expectedCount)
    assertEquals(expectedCount - 1, metric.lastStat().numSamples());
    assertEquals(expectedCount - 1, stat.getSamples());
    assertEquals(expectedCount, metric.lastStat().numSamples());
    assertEquals(expectedCount, stat.getSamples());
      String statName, int expectedStatCount, int expectedMetricCount, boolean expectError,
      int errorSleepMillis, Callable<T> callable) throws Exception {
    assertEquals(expectedMetricCount - 1, counter.value());
    assertEquals(expectedStatCount - 1, failureStat.getSamples());
    assertEquals(expectedMetricCount, counter.value());
    assertEquals(expectedStatCount, failureStat.getSamples());
