
    String identity = this.identityProvider.makeIdentity(obj);
    if (identity == null) {
      // Identity provider did not handle this
      identity = DECAYSCHEDULER_UNKNOWN_IDENTITY;
    }
