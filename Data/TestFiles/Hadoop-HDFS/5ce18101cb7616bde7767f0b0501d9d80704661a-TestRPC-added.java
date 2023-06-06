  @Test (timeout=30000)
  public void testProtocolUserPriority() throws Exception {
    final String ns = CommonConfigurationKeys.IPC_NAMESPACE + ".0";
    conf.set(CLIENT_PRINCIPAL_KEY, "clientForProtocol");
    Server server = null;
    try {
      server = setupDecayRpcSchedulerandTestServer(ns + ".");

      UserGroupInformation ugi = UserGroupInformation.createRemoteUser("user");
      // normal users start with priority 0.
      Assert.assertEquals(0, server.getPriorityLevel(ugi));
      // calls for a protocol defined client will have priority of 0.
      Assert.assertEquals(0, server.getPriorityLevel(newSchedulable(ugi)));

      // protocol defined client will have top priority of -1.
      ugi = UserGroupInformation.createRemoteUser("clientForProtocol");
      Assert.assertEquals(-1, server.getPriorityLevel(ugi));
      // calls for a protocol defined client will have priority of 0.
      Assert.assertEquals(0, server.getPriorityLevel(newSchedulable(ugi)));
    } finally {
      stop(server, null);
    }
  }

  private static Schedulable newSchedulable(UserGroupInformation ugi) {
    return new Schedulable(){
      @Override
      public UserGroupInformation getUserGroupInformation() {
        return ugi;
      }
      @Override
      public int getPriorityLevel() {
        return 0; // doesn't matter.
      }
    };
  }

