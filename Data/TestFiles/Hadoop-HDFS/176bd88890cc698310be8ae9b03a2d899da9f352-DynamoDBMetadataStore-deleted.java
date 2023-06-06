    executor = context.createThrottledExecutor();
    executor = BlockingThreadPoolExecutorService.newInstance(
        executorCapacity,
        executorCapacity * 2,
        longOption(conf, KEEPALIVE_TIME,
            DEFAULT_KEEPALIVE_TIME, 0),
        TimeUnit.SECONDS,
        "s3a-ddb-" + tableName);
