    final InetSocketAddress address = remoteId.getAddress();
    if (address.isUnresolved()) {
      throw NetUtils.wrapException(address.getHostName(),
          address.getPort(),
          null,
          0,
          new UnknownHostException());
    }

