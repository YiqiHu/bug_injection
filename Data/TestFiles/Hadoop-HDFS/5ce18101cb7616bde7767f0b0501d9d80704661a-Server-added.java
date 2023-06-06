
  @org.apache.hadoop.thirdparty.com.google.common.annotations.VisibleForTesting
  int getPriorityLevel(Schedulable e) {
    return callQueue.getPriorityLevel(e);
  }

  @org.apache.hadoop.thirdparty.com.google.common.annotations.VisibleForTesting
  int getPriorityLevel(UserGroupInformation ugi) {
    return callQueue.getPriorityLevel(ugi);
  }

  @org.apache.hadoop.thirdparty.com.google.common.annotations.VisibleForTesting
  void setPriorityLevel(UserGroupInformation ugi, int priority) {
    callQueue.setPriorityLevel(ugi, priority);
  }

