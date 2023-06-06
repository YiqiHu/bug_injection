import static org.junit.Assert.assertFalse;
    StopWatch stopWatch = new StopWatch().start();
      executorService.submit(sleeper);
      assertDidntBlock(stopWatch);
  private void assertDidntBlock(StopWatch sw) {
    try {
      assertFalse("Non-blocking call took too long.",
          sw.now(TimeUnit.MILLISECONDS) > BLOCKING_THRESHOLD_MSEC);
    } finally {
      sw.reset().start();
    }
  }

