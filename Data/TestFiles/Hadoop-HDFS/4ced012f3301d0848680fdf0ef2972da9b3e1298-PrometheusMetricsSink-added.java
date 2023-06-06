import java.util.Collection;
  private Map<String, Map<Collection<MetricsTag>, AbstractMetric>> promMetrics =
      new ConcurrentHashMap<>();
  private Map<String, Map<Collection<MetricsTag>, AbstractMetric>> nextPromMetrics =
      new ConcurrentHashMap<>();
    for (AbstractMetric metric : metricsRecord.metrics()) {
      if (metric.type() == MetricType.COUNTER
          || metric.type() == MetricType.GAUGE) {
            metricsRecord.name(), metric.name());
        nextPromMetrics.computeIfAbsent(key,
            any -> new ConcurrentHashMap<>())
            .put(metricsRecord.tags(), metric);
    promMetrics = nextPromMetrics;
    nextPromMetrics = new ConcurrentHashMap<>();
  public void init(SubsetConfiguration conf) {
    for (Map.Entry<String, Map<Collection<MetricsTag>, AbstractMetric>> promMetric :
        promMetrics.entrySet()) {
      AbstractMetric firstMetric = promMetric.getValue().values().iterator().next();

      StringBuilder builder = new StringBuilder();
      builder.append("# HELP ")
          .append(promMetric.getKey())
          .append(" ")
          .append(firstMetric.description())
          .append("\n")
          .append("# TYPE ")
          .append(promMetric.getKey())
          .append(" ")
          .append(firstMetric.type().toString().toLowerCase())
          .append("\n");

      for (Map.Entry<Collection<MetricsTag>, AbstractMetric> metric :
          promMetric.getValue().entrySet()) {
        builder.append(promMetric.getKey())
            .append("{");

        String sep = "";
        for (MetricsTag tag : metric.getKey()) {
          String tagName = tag.name().toLowerCase();

          if (!tagName.equals("numopenconnectionsperuser")) {
            builder.append(sep)
                .append(tagName)
                .append("=\"")
                .append(tag.value())
                .append("\"");
            sep = ",";
          }
        }
        builder.append("} ");
        builder.append(metric.getValue().value());
        builder.append("\n");
      }

      writer.write(builder.toString());
