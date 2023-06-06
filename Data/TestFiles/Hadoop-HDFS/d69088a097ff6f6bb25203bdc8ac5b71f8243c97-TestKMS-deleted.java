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
            reloaderStillAlive = reloaderThread.isAlive();
            if (!reloaderStillAlive) break;
