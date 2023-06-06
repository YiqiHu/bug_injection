import org.eclipse.jetty.server.Response;
        // use setStatusWithReason() to set a custom message.
        if (httpResponse instanceof Response) {
          ((Response)httpResponse).setStatusWithReason(errCode, reason);
        }

