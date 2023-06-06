  protected static TestRpcService getClient(InetSocketAddress serverAddr,
                                     Configuration clientConf)
    try {
      return RPC.getProxy(TestRpcService.class, 0, serverAddr, clientConf);
    } catch (IOException e) {
      throw new ServiceException(e);
    }
      Configuration clientConf, final RetryPolicy connectionRetryPolicy)
          connectionRetryPolicy, null).getProxy();
