import java.util.function.Supplier;
import java.util.Set;
  private static final String SSL_RELOADER_THREAD_NAME =
      "Truststore reloader thread";

        if (ssl) {
          KeyProvider testKp = createProvider(uri, conf);
          ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
          while (threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
          }
          Thread[] threads = new Thread[threadGroup.activeCount()];
          threadGroup.enumerate(threads);
          Thread reloaderThread = null;
          for (Thread thread : threads) {
            if ((thread.getName() != null)
                && (thread.getName().contains(SSL_RELOADER_THREAD_NAME))) {
              reloaderThread = thread;
            }
          }
          Assert.assertTrue("Reloader is not alive", reloaderThread.isAlive());
          // Explicitly close the provider so we can verify the internal thread
          // is shutdown
          testKp.close();
          boolean reloaderStillAlive = true;
          for (int i = 0; i < 10; i++) {
            reloaderStillAlive = reloaderThread.isAlive();
            if (!reloaderStillAlive) break;
            Thread.sleep(1000);
          }
          Assert.assertFalse("Reloader is still alive", reloaderStillAlive);
        }

              // Close the client provider. We will verify all providers'
              // Truststore reloader threads are closed later.

    // verify that providers created by KMSTokenRenewer are closed.
    if (ssl) {
      GenericTestUtils.waitFor(new Supplier<Boolean>() {
        @Override
        public Boolean get() {
          final Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
          for (Thread t : threadSet) {
            if (t.getName().contains(SSL_RELOADER_THREAD_NAME)) {
              return false;
            }
          }
          return true;
        }
      }, 1000, 10000);
    }
