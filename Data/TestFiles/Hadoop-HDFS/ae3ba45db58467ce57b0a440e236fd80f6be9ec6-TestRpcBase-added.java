import java.util.concurrent.atomic.AtomicBoolean;
  protected static TestRpcService getClient(InetSocketAddress serverAddr, Configuration clientConf)
    return getClient(serverAddr, clientConf, null);
  }

  protected static TestRpcService getClient(InetSocketAddress serverAddr,
      Configuration clientConf, RetryPolicy connectionRetryPolicy) throws ServiceException {
    return getClient(serverAddr, clientConf, connectionRetryPolicy, null);
      Configuration clientConf, final RetryPolicy connectionRetryPolicy,
      AtomicBoolean fallbackToSimpleAuth)
          connectionRetryPolicy, fallbackToSimpleAuth).getProxy();
