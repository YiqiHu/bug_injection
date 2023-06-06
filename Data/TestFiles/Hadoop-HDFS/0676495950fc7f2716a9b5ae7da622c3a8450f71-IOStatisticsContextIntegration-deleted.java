    return new IOStatisticsContextImpl(key, INSTANCE_ID.getAndIncrement());
      }
      if (ACTIVE_IOSTATS_CONTEXT.getForCurrentThread() != statisticsContext) {
