  // Although this API was removed from the interface by HADOOP-17367, we need
  // to keep it here because TestDynamometerInfra uses an old hadoop binary.
  public void authorize(UserGroupInformation user, String remoteAddress) {
    // Do nothing
  }
