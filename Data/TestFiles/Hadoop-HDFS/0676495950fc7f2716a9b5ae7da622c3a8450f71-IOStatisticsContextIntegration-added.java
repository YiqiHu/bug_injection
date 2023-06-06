    IOStatisticsContextImpl instance =
        new IOStatisticsContextImpl(key, INSTANCE_ID.getAndIncrement());
    LOG.debug("Created instance {}", instance);
    return instance;
        // new value is null, so remove it
      } else {
        // the setter is efficient in that it does not create a new
        // reference if the context is unchanged.
