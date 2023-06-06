
import org.apache.commons.lang3.ThreadUtils;
        // the first reloader thread is created here
          // the second reloader thread is created here
          Collection<Thread> reloaderThreads =
              ThreadUtils.findThreadsByName(SSL_RELOADER_THREAD_NAME);
          // now there are two active reloader threads
          assertEquals(2, reloaderThreads.size());
          // Explicitly close the provider so we can verify
          // the second reloader thread is shutdown
            for (Thread thread : reloaderThreads) {
              if (!thread.isAlive()) {
                reloaderStillAlive = false;
                break;
              }
            }
          reloaderThreads =
              ThreadUtils.findThreadsByName(SSL_RELOADER_THREAD_NAME);
          assertEquals(1, reloaderThreads.size());
