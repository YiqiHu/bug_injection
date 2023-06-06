import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;

    super(MoreExecutors.listeningDecorator(eventProcessingExecutor),
        permitCount, false);
