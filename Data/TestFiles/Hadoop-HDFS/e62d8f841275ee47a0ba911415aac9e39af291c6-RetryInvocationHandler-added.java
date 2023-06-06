import java.util.HashSet;
  private HashSet<String> failedAtLeastOnce = new HashSet<>();

    boolean info = true;
    // If this is the first failover to this proxy, skip logging at INFO level
    if (!failedAtLeastOnce.contains(proxyDescriptor.getProxyInfo().toString()))
    {
      failedAtLeastOnce.add(proxyDescriptor.getProxyInfo().toString());

      // If successful calls were made to this proxy, log info even for first
      // failover
      info = hasSuccessfulCall || asyncCallHandler.hasSuccessfulCall();
      if (!info && !LOG.isDebugEnabled()) {
        return;
      }
