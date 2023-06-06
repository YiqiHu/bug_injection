
  @Test
  public void testMetricSystemRestart() {
    MetricsSystemImpl ms = new MetricsSystemImpl("msRestartTestSystem");
    TestSink ts = new TestSink();
    String sinkName = "restartTestSink";

    try {
      ms.start();
      ms.register(sinkName, "", ts);
      assertNotNull("no adapter exists for " + sinkName,
              ms.getSinkAdapter(sinkName));
      ms.stop();

      ms.start();
      assertNotNull("no adapter exists for " + sinkName,
              ms.getSinkAdapter(sinkName));
    } finally {
      ms.stop();
    }
  }
