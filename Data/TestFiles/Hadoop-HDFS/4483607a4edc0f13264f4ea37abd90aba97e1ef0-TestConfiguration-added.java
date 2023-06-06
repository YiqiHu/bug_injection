import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicBoolean;

  @Test
  public void testConcurrentModificationDuringIteration() throws InterruptedException {
    Configuration configuration = new Configuration();
    new Thread(() -> {
      while (true) {
        configuration.set(String.valueOf(Math.random()), String.valueOf(Math.random()));
      }
    }).start();

    AtomicBoolean exceptionOccurred = new AtomicBoolean(false);

    new Thread(() -> {
      while (true) {
        try {
          configuration.iterator();
        } catch (final ConcurrentModificationException e) {
          exceptionOccurred.set(true);
          break;
        }
      }
    }).start();

    Thread.sleep(1000); //give enough time for threads to run

    assertFalse("ConcurrentModificationException occurred", exceptionOccurred.get());
  }
