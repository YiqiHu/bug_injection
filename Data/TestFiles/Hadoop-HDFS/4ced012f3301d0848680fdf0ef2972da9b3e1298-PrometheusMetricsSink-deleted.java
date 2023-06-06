  private final Map<String, String> metricLines = new ConcurrentHashMap<>();
    for (AbstractMetric metrics : metricsRecord.metrics()) {
      if (metrics.type() == MetricType.COUNTER
          || metrics.type() == MetricType.GAUGE) {
            metricsRecord.name(), metrics.name());

        StringBuilder builder = new StringBuilder();
        builder.append("# TYPE ")
            .append(key)
            .append(" ")
            .append(metrics.type().toString().toLowerCase())
            .append("\n")
            .append(key)
            .append("{");
        String sep = "";

        //add tags
        for (MetricsTag tag : metricsRecord.tags()) {
          String tagName = tag.name().toLowerCase();

          //ignore specific tag which includes sub-hierarchy
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
        builder.append(metrics.value());
        builder.append("\n");
        metricLines.put(key, builder.toString());

  public void init(SubsetConfiguration subsetConfiguration) {

    for (String line : metricLines.values()) {
      writer.write(line);
