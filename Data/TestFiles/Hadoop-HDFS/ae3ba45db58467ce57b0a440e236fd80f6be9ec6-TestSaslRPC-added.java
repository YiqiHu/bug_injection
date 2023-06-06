import java.util.concurrent.atomic.AtomicBoolean;
  /**
   * In DfsClient there is a fallback mechanism to simple auth, which passes in an atomic boolean
   * to the ipc Client, which then sets it during setupIOStreams.
   * SetupIOStreams were running only once per connection, so if two separate DfsClient was
   * instantiated, then due to the connection caching inside the ipc client, the second DfsClient
   * did not have the passed in atomic boolean set properly if the first client was not yet closed,
   * as setupIOStreams was yielding to set up new streams as it has reused the already existing
   * connection.
   * This test mimics this behaviour, and asserts the fallback whether it is set correctly.
   * @see <a href="https://issues.apache.org/jira/browse/HADOOP-17975">HADOOP-17975</a>
   */
  @Test
  public void testClientFallbackToSimpleAuthForASecondClient() throws Exception {
    Configuration serverConf = createConfForAuth(SIMPLE);
    Server server = startServer(serverConf,
        setupServerUgi(SIMPLE, serverConf),
        createServerSecretManager(SIMPLE, new TestTokenSecretManager()));
    final InetSocketAddress serverAddress = NetUtils.getConnectAddress(server);

    clientFallBackToSimpleAllowed = true;
    Configuration clientConf = createConfForAuth(KERBEROS);
    UserGroupInformation clientUgi = setupClientUgi(KERBEROS, clientConf);

    AtomicBoolean fallbackToSimpleAuth1 = new AtomicBoolean();
    AtomicBoolean fallbackToSimpleAuth2 = new AtomicBoolean();
    try {
      LOG.info("trying ugi:"+ clientUgi +" tokens:"+ clientUgi.getTokens());
      clientUgi.doAs((PrivilegedExceptionAction<Void>) () -> {
        TestRpcService proxy1 = null;
        TestRpcService proxy2 = null;
        try {
          proxy1 = getClient(serverAddress, clientConf, null, fallbackToSimpleAuth1);
          proxy1.ping(null, newEmptyRequest());
          // make sure the other side thinks we are who we said we are!!!
          assertEquals(clientUgi.getUserName(),
              proxy1.getAuthUser(null, newEmptyRequest()).getUser());
          AuthMethod authMethod =
              convert(proxy1.getAuthMethod(null, newEmptyRequest()));
          assertAuthEquals(SIMPLE, authMethod.toString());

          proxy2 = getClient(serverAddress, clientConf, null, fallbackToSimpleAuth2);
          proxy2.ping(null, newEmptyRequest());
          // make sure the other side thinks we are who we said we are!!!
          assertEquals(clientUgi.getUserName(),
              proxy2.getAuthUser(null, newEmptyRequest()).getUser());
          AuthMethod authMethod2 =
              convert(proxy2.getAuthMethod(null, newEmptyRequest()));
          assertAuthEquals(SIMPLE, authMethod2.toString());
        } finally {
          if (proxy1 != null) {
            RPC.stopProxy(proxy1);
          }
          if (proxy2 != null) {
            RPC.stopProxy(proxy2);
          }
        }
        return null;
      });
    } finally {
      server.stop();
    }

    assertTrue("First client does not set to fall back properly.", fallbackToSimpleAuth1.get());
    assertTrue("Second client does not set to fall back properly.", fallbackToSimpleAuth2.get());
  }



    Configuration serverConf = createConfForAuth(serverAuth);
    Server server = startServer(
        serverConf,
        setupServerUgi(serverAuth, serverConf),
        createServerSecretManager(serverAuth, sm));
    final InetSocketAddress serverAddress = NetUtils.getConnectAddress(server);

    final Configuration clientConf = createConfForAuth(clientAuth);
    final UserGroupInformation clientUgi = setupClientUgi(clientAuth, clientConf);

    setupTokenIfNeeded(tokenType, sm, clientUgi, serverAddress);

    try {
      return createClientAndQueryAuthMethod(serverAddress, clientConf, clientUgi, null);
    } finally {
      server.stop();
    }
  }

  private Configuration createConfForAuth(AuthMethod clientAuth) {
    final Configuration clientConf = new Configuration(conf);
    clientConf.set(HADOOP_SECURITY_AUTHENTICATION, clientAuth.toString());
    clientConf.setBoolean(
        CommonConfigurationKeys.IPC_CLIENT_FALLBACK_TO_SIMPLE_AUTH_ALLOWED_KEY,
        clientFallBackToSimpleAllowed);
    return clientConf;
  }

  private SecretManager<?> createServerSecretManager(
      AuthMethod serverAuth, TestTokenSecretManager sm) {
    return serverSm;
  }
  private Server startServer(Configuration serverConf, UserGroupInformation serverUgi,
      SecretManager<?> serverSm) throws IOException, InterruptedException {
    return server;
  }
  private UserGroupInformation setupServerUgi(AuthMethod serverAuth,
      Configuration serverConf) {
    UserGroupInformation.setConfiguration(serverConf);

    final UserGroupInformation serverUgi = (serverAuth == KERBEROS)
        ? UserGroupInformation.createRemoteUser("server/localhost@NONE")
        : UserGroupInformation.createRemoteUser("server");
    serverUgi.setAuthenticationMethod(serverAuth);
    return serverUgi;
  }

  private UserGroupInformation setupClientUgi(AuthMethod clientAuth,
      Configuration clientConf) {

    clientUgi.setAuthenticationMethod(clientAuth);
    return clientUgi;
  }
  private void setupTokenIfNeeded(UseToken tokenType, TestTokenSecretManager sm,
      UserGroupInformation clientUgi, InetSocketAddress addr) {
  }
  private String createClientAndQueryAuthMethod(InetSocketAddress serverAddress,
      Configuration clientConf, UserGroupInformation clientUgi, AtomicBoolean fallbackToSimpleAuth)
      throws IOException, InterruptedException {
    LOG.info("trying ugi:"+ clientUgi +" tokens:"+ clientUgi.getTokens());
    return clientUgi.doAs(new PrivilegedExceptionAction<String>() {
      @Override
      public String run() throws IOException {
        TestRpcService proxy = null;
        try {
          proxy = getClient(serverAddress, clientConf, null, fallbackToSimpleAuth);

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
