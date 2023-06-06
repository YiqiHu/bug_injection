    // log info if this has made some successful calls or
    // this is not the first failover
    final boolean info = hasSuccessfulCall || failovers != 0
        || asyncCallHandler.hasSuccessfulCall();
    if (!info && !LOG.isDebugEnabled()) {
      return;
