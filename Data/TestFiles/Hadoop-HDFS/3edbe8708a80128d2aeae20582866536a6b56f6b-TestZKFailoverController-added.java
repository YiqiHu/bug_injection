import java.net.InetSocketAddress;
import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.security.authorize.PolicyProvider;
import org.apache.hadoop.security.authorize.RefreshAuthorizationPolicyProtocol;
import org.apache.hadoop.security.authorize.Service;
import org.apache.hadoop.test.LambdaTestUtils;
  @Test
  public void testPolicyProviderForZKFCRpcServer() throws Exception {
    Configuration myconf = new Configuration();
    myconf.setBoolean(CommonConfigurationKeys.HADOOP_SECURITY_AUTHORIZATION,
        true);

    DummyHAService dummyHAService = new DummyHAService(HAServiceState.ACTIVE,
        new InetSocketAddress(0), false);
    MiniZKFCCluster.DummyZKFC dummyZKFC =
        new MiniZKFCCluster.DummyZKFC(myconf, dummyHAService);

    // initialize ZKFCRpcServer with null policy
    LambdaTestUtils.intercept(HadoopIllegalArgumentException.class,
        CommonConfigurationKeys.HADOOP_SECURITY_AUTHORIZATION
            + "is configured to true but service-level"
            + "authorization security policy is null.",
        () -> new ZKFCRpcServer(myconf, new InetSocketAddress(0),
            dummyZKFC, null));

    // initialize ZKFCRpcServer with dummy policy
    PolicyProvider dummyPolicy = new PolicyProvider() {
      private final Service[] services = new Service[] {
          new Service(CommonConfigurationKeys.SECURITY_ZKFC_PROTOCOL_ACL,
              ZKFCProtocol.class),
          new Service(
              CommonConfigurationKeys.HADOOP_SECURITY_SERVICE_AUTHORIZATION_REFRESH_POLICY,
              RefreshAuthorizationPolicyProtocol.class),
      };
      @Override
      public Service[] getServices() {
        return this.services;
      }
    };

    ZKFCRpcServer server = new ZKFCRpcServer(myconf,
        new InetSocketAddress(0), dummyZKFC, dummyPolicy);
    server.start();
    server.stopAndJoin();
  }

