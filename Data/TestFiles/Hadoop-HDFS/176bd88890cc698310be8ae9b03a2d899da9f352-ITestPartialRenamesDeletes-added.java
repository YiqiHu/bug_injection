import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;;
      MoreExecutors.listeningDecorator(
          BlockingThreadPoolExecutorService.newInstance(
              EXECUTOR_THREAD_COUNT,
              EXECUTOR_THREAD_COUNT * 2,
              30, TimeUnit.SECONDS,
              "test-operations"));
