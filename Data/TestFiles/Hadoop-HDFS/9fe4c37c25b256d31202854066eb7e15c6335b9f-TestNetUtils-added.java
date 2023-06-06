
  @Test
  public void testInvalidAddress() throws Throwable {
    Configuration conf = new Configuration();

    Socket socket = NetUtils.getDefaultSocketFactory(conf)
        .createSocket();
    socket.bind(new InetSocketAddress("127.0.0.1", 0));
    try {
      NetUtils.connect(socket,
          new InetSocketAddress("invalid-test-host",
              0), 20000);
      socket.close();
      fail("Should not have connected");
    } catch (UnknownHostException uhe) {
      LOG.info("Got exception: ", uhe);
    }
  }

