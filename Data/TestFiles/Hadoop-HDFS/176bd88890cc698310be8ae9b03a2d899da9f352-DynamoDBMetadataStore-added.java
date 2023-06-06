import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;;
    executor = MoreExecutors.listeningDecorator(
        context.createThrottledExecutor());
    executor = MoreExecutors.listeningDecorator(
        BlockingThreadPoolExecutorService.newInstance(
            executorCapacity,
            executorCapacity * 2,
              longOption(conf, KEEPALIVE_TIME,
                  DEFAULT_KEEPALIVE_TIME, 0),
                  TimeUnit.SECONDS,
                  "s3a-ddb-" + tableName));
