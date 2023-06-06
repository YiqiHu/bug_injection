import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.metrics2.lib.MutableRollingAverages;
import org.apache.hadoop.test.GenericTestUtils;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PEER_METRICS_MIN_OUTLIER_DETECTION_SAMPLES_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PEER_STATS_ENABLED_KEY;
import static org.junit.Assert.assertEquals;
    conf.setBoolean(DFS_DATANODE_PEER_STATS_ENABLED_KEY, true);
  @Test(timeout = 30000)
  public void testRemoveStaleRecord() throws Exception {
    final int numWindows = 5;
    final long scheduleInterval = 1000;
    final int iterations = 3;
    final int numSamples = 100;

    final Configuration conf = new HdfsConfiguration();
    conf.setLong(DFS_DATANODE_PEER_METRICS_MIN_OUTLIER_DETECTION_SAMPLES_KEY,
        numSamples);
    conf.setBoolean(DFS_DATANODE_PEER_STATS_ENABLED_KEY, true);

    final DataNodePeerMetrics peerMetrics =
        DataNodePeerMetrics.create("Sample-DataNode", conf);
    MutableRollingAverages rollingAverages =
        peerMetrics.getSendPacketDownstreamRollingAverages();
    rollingAverages.setRecordValidityMs(numWindows * scheduleInterval);
    MetricsTestHelper.replaceRollingAveragesScheduler(rollingAverages,
        numWindows, scheduleInterval, TimeUnit.MILLISECONDS);

    List<String> peerAddrList = new ArrayList<>();
    for (int i = 1; i <= iterations; i++) {
      peerAddrList.add(genPeerAddress());
    }
    for (String peerAddr : peerAddrList) {
      for (int j = 1; j <= numSamples; j++) {
        /* simulate to get latency of 1 to 1000 ms */
        final long latency = ThreadLocalRandom.current().nextLong(1, 1000);
        peerMetrics.addSendPacketDownstream(peerAddr, latency);
      }
    }

    GenericTestUtils.waitFor(
        () -> rollingAverages.getStats(numSamples).size() > 0, 500, 5000);
    assertEquals(3, rollingAverages.getStats(numSamples).size());
    /* wait for stale report to be removed */
    GenericTestUtils.waitFor(
        () -> rollingAverages.getStats(numSamples).isEmpty(), 500, 10000);
    assertEquals(0, rollingAverages.getStats(numSamples).size());

    /* dn can report peer metrics normally when it added back to cluster */
    for (String peerAddr : peerAddrList) {
      for (int j = 1; j <= numSamples; j++) {
        /* simulate to get latency of 1 to 1000 ms */
        final long latency = ThreadLocalRandom.current().nextLong(1, 1000);
        peerMetrics.addSendPacketDownstream(peerAddr, latency);
      }
    }
    GenericTestUtils.waitFor(
        () -> rollingAverages.getStats(numSamples).size() > 0, 500, 10000);
    assertEquals(3, rollingAverages.getStats(numSamples).size());
  }

