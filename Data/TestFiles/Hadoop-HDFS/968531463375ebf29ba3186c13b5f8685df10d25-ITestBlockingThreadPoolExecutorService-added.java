import java.util.concurrent.CountDownLatch;
    CountDownLatch latch = new CountDownLatch(1);
      executorService.submit(new LatchedSleeper(latch));
    StopWatch stopWatch = new StopWatch().start();
    latch.countDown();
  private class LatchedSleeper implements Runnable {
    private final CountDownLatch latch;

    LatchedSleeper(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      try {
        latch.await();
        Thread.sleep(TASK_SLEEP_MSEC);
      } catch (InterruptedException e) {
        LOG.info("Thread {} interrupted.", Thread.currentThread().getName());
        Thread.currentThread().interrupt();
      }
    }
  }

