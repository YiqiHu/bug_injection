  /**
   * Metrics to track token management operations.
   */
  private DelegationTokenSecretManagerMetrics metrics;
    this.metrics = DelegationTokenSecretManagerMetrics.create();
      metrics.trackStoreToken(() -> storeToken(identifier, tokenInfo));
    metrics.trackUpdateToken(() -> updateToken(id, info));
    metrics.trackRemoveToken(() -> {
    return metrics;
