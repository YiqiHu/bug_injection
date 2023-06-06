    public void onSuccess(VolumeCheckResult result) {
      if (result == null) {
        LOG.error("Unexpected health check result null for volume {}",
      } else {
        switch(result) {
        case HEALTHY:
        case DEGRADED:
          LOG.debug("Volume {} is {}.", reference.getVolume(), result);
          markHealthy();
          break;
        case FAILED:
          LOG.warn("Volume {} detected as being unhealthy",
              reference.getVolume());
          markFailed();
          break;
        default:
          LOG.error("Unexpected health check result {} for volume {}",
              result, reference.getVolume());
          markHealthy();
          break;
        }
