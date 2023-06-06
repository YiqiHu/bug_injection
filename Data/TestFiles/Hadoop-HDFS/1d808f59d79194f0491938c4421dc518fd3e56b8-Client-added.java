          if (server.isUnresolved()) {
            // Jump into the catch block. updateAddress() will re-resolve
            // the address if this is just a temporary DNS failure. If not,
            // it will timeout after max ipc client retries
            throw NetUtils.wrapException(server.getHostName(),
                server.getPort(),
                NetUtils.getHostname(),
                0,
                new UnknownHostException());
          }
