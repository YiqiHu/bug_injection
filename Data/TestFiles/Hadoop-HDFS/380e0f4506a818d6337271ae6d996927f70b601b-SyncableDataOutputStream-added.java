import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.classification.InterfaceAudience;
  private static final Logger LOG = LoggerFactory.getLogger(SyncableDataOutputStream.class);


  @Override
  public void close() throws IOException {
    IOException ioeFromFlush = null;
    try {
      flush();
    } catch (IOException e) {
      ioeFromFlush = e;
      throw e;
    } finally {
      try {
        this.out.close();
      } catch (IOException e) {
        // If there was an Exception during flush(), the Azure SDK will throw back the
        // same when we call close on the same stream. When try and finally both throw
        // Exception, Java will use Throwable#addSuppressed for one of the Exception so
        // that the caller will get one exception back. When within this, if both
        // Exceptions are equal, it will throw back IllegalStateException. This makes us
        // to throw back a non IOE. The below special handling is to avoid this.
        if (ioeFromFlush == e) {
          // Do nothing..
          // The close() call gave back the same IOE which flush() gave. Just swallow it
          LOG.debug("flush() and close() throwing back same Exception. Just swallowing the latter", e);
        } else {
          // Let Java handle 2 different Exceptions been thrown from try and finally.
          throw e;
        }
      }
    }
  }
