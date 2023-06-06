import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.server.MiniYARNCluster;
  private MiniYARNCluster yarnCluster;

    conf.set("yarn.scheduler.capacity.root.queues", "default");
    conf.setInt("yarn.scheduler.capacity.root.default.capacity", 100);
    yarnCluster = new MiniYARNCluster(getClass().getName(), 1, 1, 1, 1);
    yarnCluster.init(conf);
    yarnCluster.start();
    IOUtils.cleanupWithLogger(LOG, yarnCluster);
