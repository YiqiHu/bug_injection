      BlockingThreadPoolExecutorService.newInstance(
          EXECUTOR_THREAD_COUNT,
          EXECUTOR_THREAD_COUNT * 2,
          30, TimeUnit.SECONDS,
          "test-operations");
