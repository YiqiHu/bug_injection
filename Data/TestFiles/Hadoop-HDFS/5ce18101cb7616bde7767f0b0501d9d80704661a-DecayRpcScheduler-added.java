
import org.apache.hadoop.security.UserGroupInformation;
  private final Map<String, Integer> staticPriorities = new HashMap<>();
    Integer staticPriority = staticPriorities.get(identity);
    if (staticPriority != null) {
      return staticPriority.intValue();
    }
  private String getIdentity(Schedulable obj) {
    String identity = this.identityProvider.makeIdentity(obj);
    if (identity == null) {
      // Identity provider did not handle this
      identity = DECAYSCHEDULER_UNKNOWN_IDENTITY;
    }
    return identity;
  }

    String identity = getIdentity(obj);
    // highest priority users may have a negative priority but their
    // calls will be priority 0.
    return Math.max(0, cachedOrComputedPriorityLevel(identity));
  }
  @VisibleForTesting
  int getPriorityLevel(UserGroupInformation ugi) {
    String identity = getIdentity(newSchedulable(ugi));
    // returns true priority of the user.
  @VisibleForTesting
  void setPriorityLevel(UserGroupInformation ugi, int priority) {
    String identity = getIdentity(newSchedulable(ugi));
    priority = Math.min(numLevels - 1, priority);
    LOG.info("Setting priority for user:" + identity + "=" + priority);
    staticPriorities.put(identity, priority);
  }

  // dummy instance to conform to identity provider api.
  private static Schedulable newSchedulable(UserGroupInformation ugi) {
    return new Schedulable() {
      @Override
      public UserGroupInformation getUserGroupInformation() {
        return ugi;
      }

      @Override
      public int getPriorityLevel() {
        return 0;
      }
    };
  }

