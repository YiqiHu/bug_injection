import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.ForwardingListeningExecutorService;
import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.ListenableFuture;
import org.apache.hadoop.thirdparty.com.google.common.util.concurrent.ListeningExecutorService;
    ForwardingListeningExecutorService {
  private final ListeningExecutorService executorDelegatee;
      ListeningExecutorService executorDelegatee,
      ListeningExecutorService executorDelegatee,
  protected ListeningExecutorService delegate() {
  public <T> ListenableFuture<T> submit(Callable<T> task) {
  public <T> ListenableFuture<T> submit(Runnable task, T result) {
  public ListenableFuture<?> submit(Runnable task) {
