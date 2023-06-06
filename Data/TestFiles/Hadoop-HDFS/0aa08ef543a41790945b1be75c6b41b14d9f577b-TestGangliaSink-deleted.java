 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
    @Test
    public void testShouldCreateDatagramSocketByDefault() throws Exception {
        SubsetConfiguration conf = new ConfigBuilder()
                .subset("test.sink.ganglia");
        GangliaSink30 gangliaSink = new GangliaSink30();
        gangliaSink.init(conf);
        DatagramSocket socket = gangliaSink.getDatagramSocket();
        assertFalse("Did not create DatagramSocket", socket == null || socket instanceof MulticastSocket);
    }
    @Test
    public void testShouldCreateDatagramSocketIfMulticastIsDisabled() throws Exception {
        SubsetConfiguration conf = new ConfigBuilder()
                .add("test.sink.ganglia.multicast", false)
                .subset("test.sink.ganglia");
        GangliaSink30 gangliaSink = new GangliaSink30();
        gangliaSink.init(conf);
        DatagramSocket socket = gangliaSink.getDatagramSocket();
        assertFalse("Did not create DatagramSocket", socket == null || socket instanceof MulticastSocket);
    }
    @Test
    public void testShouldCreateMulticastSocket() throws Exception {
        SubsetConfiguration conf = new ConfigBuilder()
                .add("test.sink.ganglia.multicast", true)
                .subset("test.sink.ganglia");
        GangliaSink30 gangliaSink = new GangliaSink30();
        gangliaSink.init(conf);
        DatagramSocket socket = gangliaSink.getDatagramSocket();
        assertTrue("Did not create MulticastSocket", socket != null && socket instanceof MulticastSocket);
        int ttl = ((MulticastSocket) socket).getTimeToLive();
        assertEquals("Did not set default TTL", 1, ttl);
    }
    @Test
    public void testShouldSetMulticastSocketTtl() throws Exception {
        SubsetConfiguration conf = new ConfigBuilder()
                .add("test.sink.ganglia.multicast", true)
                .add("test.sink.ganglia.multicast.ttl", 3)
                .subset("test.sink.ganglia");
        GangliaSink30 gangliaSink = new GangliaSink30();
        gangliaSink.init(conf);
        DatagramSocket socket = gangliaSink.getDatagramSocket();
        assertTrue("Did not create MulticastSocket", socket != null && socket instanceof MulticastSocket);
        int ttl = ((MulticastSocket) socket).getTimeToLive();
        assertEquals("Did not set TTL", 3, ttl);
    }
