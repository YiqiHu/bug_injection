import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;
        MoreExecutors.listeningDecorator(
            BlockingThreadPoolExecutorService.newInstance(count * 2, 0,
            "creation-threads"));
