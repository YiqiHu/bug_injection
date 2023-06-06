import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;;
    executor = MoreExecutors.listeningDecorator(
        context.createThrottledExecutor(1));
