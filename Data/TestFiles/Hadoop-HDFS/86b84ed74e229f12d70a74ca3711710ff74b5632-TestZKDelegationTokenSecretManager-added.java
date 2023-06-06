import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.ProtectACLCreateModeStatPathAndBytesable;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.zookeeper.KeeperException;

    tm1.destroy();
    curatorFramework.close();
  }

  @Test
  public void testCreateNameSpaceRepeatedly() throws Exception {

    String connectString = zkServer.getConnectString();
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    Configuration conf = getSecretConf(connectString);
    CuratorFramework curatorFramework =
        CuratorFrameworkFactory.builder().
        connectString(connectString).
        retryPolicy(retryPolicy).
        build();
    curatorFramework.start();

    String workingPath = "/" + conf.get(ZKDelegationTokenSecretManager.ZK_DTSM_ZNODE_WORKING_PATH,
        ZKDelegationTokenSecretManager.ZK_DTSM_ZNODE_WORKING_PATH_DEAFULT) + "/ZKDTSMRoot-Test";
    CreateBuilder createBuilder = curatorFramework.create();
    ProtectACLCreateModeStatPathAndBytesable<String> createModeStat =
        createBuilder.creatingParentContainersIfNeeded();
    createModeStat.forPath(workingPath);

    // Check if the created NameSpace exists.
    Stat stat = curatorFramework.checkExists().forPath(workingPath);
    Assert.assertNotNull(stat);

    // Repeated creation will throw NodeExists exception
    LambdaTestUtils.intercept(KeeperException.class,
        "KeeperErrorCode = NodeExists for "+workingPath,
        () -> createModeStat.forPath(workingPath));
