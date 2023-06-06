      //Only increment bytesReceived counter when the status code is 2XX.
      if (httpOperation.getStatusCode() >= HttpURLConnection.HTTP_OK
          && httpOperation.getStatusCode() <= HttpURLConnection.HTTP_PARTIAL) {
        incrementCounter(AbfsStatistic.BYTES_RECEIVED,
            httpOperation.getBytesReceived());
      }
