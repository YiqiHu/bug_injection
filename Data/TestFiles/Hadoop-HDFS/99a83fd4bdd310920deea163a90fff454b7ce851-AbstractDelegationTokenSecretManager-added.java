  /**
   * Metrics to track token management operations.
   */
  private static final DelegationTokenSecretManagerMetrics METRICS
      = DelegationTokenSecretManagerMetrics.create();

      METRICS.trackStoreToken(() -> storeToken(identifier, tokenInfo));
    METRICS.trackUpdateToken(() -> updateToken(id, info));
    METRICS.trackRemoveToken(() -> {
    return METRICS;
