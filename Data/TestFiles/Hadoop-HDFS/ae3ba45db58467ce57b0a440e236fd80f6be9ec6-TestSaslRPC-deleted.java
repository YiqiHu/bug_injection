

    final Configuration serverConf = new Configuration(conf);
    serverConf.set(HADOOP_SECURITY_AUTHENTICATION, serverAuth.toString());
    UserGroupInformation.setConfiguration(serverConf);

    final UserGroupInformation serverUgi = (serverAuth == KERBEROS)
        ? UserGroupInformation.createRemoteUser("server/localhost@NONE")
        : UserGroupInformation.createRemoteUser("server");
    serverUgi.setAuthenticationMethod(serverAuth);
    final Configuration clientConf = new Configuration(conf);
    clientConf.set(HADOOP_SECURITY_AUTHENTICATION, clientAuth.toString());
    clientConf.setBoolean(
        CommonConfigurationKeys.IPC_CLIENT_FALLBACK_TO_SIMPLE_AUTH_ALLOWED_KEY,
        clientFallBackToSimpleAllowed);

    clientUgi.setAuthenticationMethod(clientAuth);
    final InetSocketAddress addr = NetUtils.getConnectAddress(server);
    try {
      LOG.info("trying ugi:"+clientUgi+" tokens:"+clientUgi.getTokens());
      return clientUgi.doAs(new PrivilegedExceptionAction<String>() {
        @Override
        public String run() throws IOException {
          TestRpcService proxy = null;
          try {
            proxy = getClient(addr, clientConf);

            proxy.ping(null, newEmptyRequest());
            // make sure the other side thinks we are who we said we are!!!
            assertEquals(clientUgi.getUserName(),
                proxy.getAuthUser(null, newEmptyRequest()).getUser());
            AuthMethod authMethod =
                convert(proxy.getAuthMethod(null, newEmptyRequest()));
            // verify sasl completed with correct QOP
            assertEquals((authMethod != SIMPLE) ? expectedQop.saslQop : null,
                         RPC.getConnectionIdForProxy(proxy).getSaslQop());
            return authMethod != null ? authMethod.toString() : null;
          } catch (ServiceException se) {
            if (se.getCause() instanceof RemoteException) {
              throw (RemoteException) se.getCause();
            } else if (se.getCause() instanceof IOException) {
              throw (IOException) se.getCause();
            } else {
              throw new RuntimeException(se.getCause());
            }
          } finally {
            if (proxy != null) {
              RPC.stopProxy(proxy);
            }
      });
    } finally {
      server.stop();
    }
