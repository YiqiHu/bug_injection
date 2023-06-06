import org.apache.hadoop.security.UserGroupInformation;
  int getPriorityLevel(UserGroupInformation user) {
    if (scheduler instanceof DecayRpcScheduler) {
      return ((DecayRpcScheduler)scheduler).getPriorityLevel(user);
    }
    return 0;
  }

  void setPriorityLevel(UserGroupInformation user, int priority) {
    if (scheduler instanceof DecayRpcScheduler) {
      ((DecayRpcScheduler)scheduler).setPriorityLevel(user, priority);
    }
  }

