      LOG.debug("Setup connection to " + server.toString());
            try {
              // HADOOP-17068: when server changed, ignore the exception.
              handleConnectionFailure(ioFailures++, ie);
            } catch (IOException ioe) {
              LOG.warn("Exception when handle ConnectionFailure: "
                  + ioe.getMessage());
            }
          } else {
            handleConnectionFailure(ioFailures++, ie);
        // Log the newest server information if update address.
