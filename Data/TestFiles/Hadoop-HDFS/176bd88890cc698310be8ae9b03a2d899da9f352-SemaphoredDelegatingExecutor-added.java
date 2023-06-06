import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.ForwardingExecutorService;
import java.util.concurrent.ExecutorService;
    ForwardingExecutorService {
  private final ExecutorService executorDelegatee;
      ExecutorService executorDelegatee,
      ExecutorService executorDelegatee,
  protected ExecutorService delegate() {
  public <T> Future<T> submit(Callable<T> task) {
  public <T> Future<T> submit(Runnable task, T result) {
  public Future<?> submit(Runnable task) {
