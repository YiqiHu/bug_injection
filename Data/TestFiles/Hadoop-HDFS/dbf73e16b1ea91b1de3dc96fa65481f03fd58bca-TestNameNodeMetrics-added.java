
import org.apache.hadoop.hdfs.server.namenode.NameNodeRpcServer;
import org.apache.hadoop.ipc.metrics.RpcDetailedMetrics;

  @Test
  public void testNNRPCMetricIntegrity() {
    RpcDetailedMetrics metrics =
        ((NameNodeRpcServer) cluster.getNameNode()
            .getRpcServer()).getClientRpcServer().getRpcDetailedMetrics();
    MetricsRecordBuilder rb = getMetrics(metrics.name());
    // CommitBlockSynchronizationNumOps should exist.
    assertCounter("CommitBlockSynchronizationNumOps", 0L, rb);
  }
