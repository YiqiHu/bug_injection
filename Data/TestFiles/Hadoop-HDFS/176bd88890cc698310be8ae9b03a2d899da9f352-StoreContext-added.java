import java.util.concurrent.ExecutorService;
import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.MoreExecutors;
      final ExecutorService executor,
    this.executor = MoreExecutors.listeningDecorator(executor);
  public ExecutorService getExecutor() {
  public ExecutorService createThrottledExecutor(int capacity) {
  public ExecutorService createThrottledExecutor() {
