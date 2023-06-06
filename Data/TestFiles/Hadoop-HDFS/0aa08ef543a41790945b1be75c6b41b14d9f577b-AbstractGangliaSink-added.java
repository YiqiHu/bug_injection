import java.util.ArrayList;
  public List<? extends SocketAddress> getMetricsServers() {
    return metricsServers;
  }

    List<String> serversFromConf =
        conf.getList(String.class, SERVERS_PROPERTY, new ArrayList<String>());
    metricsServers =
        Servers.parse(serversFromConf.size() > 0 ? String.join(",", serversFromConf) : null,
            DEFAULT_PORT);
