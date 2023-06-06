    public void onSuccess(@Nonnull VolumeCheckResult result) {
      switch(result) {
      case HEALTHY:
      case DEGRADED:
        LOG.debug("Volume {} is {}.", reference.getVolume(), result);
        markHealthy();
        break;
      case FAILED:
        LOG.warn("Volume {} detected as being unhealthy",
        markFailed();
        break;
      default:
        LOG.error("Unexpected health check result {} for volume {}",
            result, reference.getVolume());
        break;
