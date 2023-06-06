import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import org.apache.hadoop.test.LambdaTestUtils;
  @Test(timeout=60000)
  public void testIpcHostResolutionTimeout() throws Exception {
    final InetSocketAddress addr = new InetSocketAddress("host.invalid", 80);

    // start client
    Client.setConnectTimeout(conf, 100);
    final Client client = new Client(LongWritable.class, conf);
    // set the rpc timeout to twice the MIN_SLEEP_TIME
    try {
      LambdaTestUtils.intercept(UnknownHostException.class,
          new Callable<Void>() {
            @Override
            public Void call() throws IOException {
              TestIPC.this.call(client, new LongWritable(RANDOM.nextLong()),
                  addr, MIN_SLEEP_TIME * 2, conf);
              return null;
            }
          });
    } finally {
      client.stop();
    }
  }

  @Test(timeout=60000)
  public void testIpcFlakyHostResolution() throws IOException {
    // start server
    Server server = new TestServer(5, false);
    server.start();

    // Leave host unresolved to start. Use "localhost" as opposed
    // to local IP from NetUtils.getConnectAddress(server) to force
    // resolution later
    InetSocketAddress unresolvedAddr = InetSocketAddress.createUnresolved(
        "localhost", NetUtils.getConnectAddress(server).getPort());

    // start client
    Client.setConnectTimeout(conf, 100);
    Client client = new Client(LongWritable.class, conf);

    try {
      // Should re-resolve host and succeed
      call(client, new LongWritable(RANDOM.nextLong()), unresolvedAddr,
          MIN_SLEEP_TIME * 2, conf);
    } finally {
      client.stop();
      server.stop();
    }
  }

