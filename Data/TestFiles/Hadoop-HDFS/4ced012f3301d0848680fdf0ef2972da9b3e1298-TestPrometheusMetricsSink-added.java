import org.apache.hadoop.metrics2.annotation.Metric.Type;
    metrics.unregisterSource("TestMetrics");
    metrics.stop();
    metrics.shutdown();
  }

  /**
   * Fix for HADOOP-17804, make sure Prometheus metrics get deduped based on metric
   * and tags, not just the metric.
   */
  @Test
  public void testPublishMultiple() throws IOException {
    //GIVEN
    MetricsSystem metrics = DefaultMetricsSystem.instance();

    metrics.init("test");
    PrometheusMetricsSink sink = new PrometheusMetricsSink();
    metrics.register("Prometheus", "Prometheus", sink);
    TestMetrics testMetrics1 = metrics
        .register("TestMetrics1", "Testing metrics", new TestMetrics("1"));
    TestMetrics testMetrics2 = metrics
        .register("TestMetrics2", "Testing metrics", new TestMetrics("2"));

    testMetrics1.numBucketCreateFails.incr();
    testMetrics2.numBucketCreateFails.incr();
    metrics.publishMetricsNow();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(stream, UTF_8);

    //WHEN
    sink.writeMetrics(writer);
    writer.flush();

    //THEN
    String writtenMetrics = stream.toString(UTF_8.name());
    System.out.println(writtenMetrics);
    Assert.assertTrue(
        "The expected first metric line is missing from prometheus metrics output",
        writtenMetrics.contains(
            "test_metrics_num_bucket_create_fails{context=\"dfs\",testtag=\"testTagValue1\"")
    );
    Assert.assertTrue(
        "The expected second metric line is missing from prometheus metrics output",
        writtenMetrics.contains(
            "test_metrics_num_bucket_create_fails{context=\"dfs\",testtag=\"testTagValue2\"")
    );

    metrics.unregisterSource("TestMetrics1");
    metrics.unregisterSource("TestMetrics2");
    metrics.stop();
    metrics.shutdown();
  }

  /**
   * Fix for HADOOP-17804, make sure Prometheus metrics start fresh after each flush.
   */
  @Test
  public void testPublishFlush() throws IOException {
    //GIVEN
    MetricsSystem metrics = DefaultMetricsSystem.instance();

    metrics.init("test");
    PrometheusMetricsSink sink = new PrometheusMetricsSink();
    metrics.register("Prometheus", "Prometheus", sink);
    TestMetrics testMetrics = metrics
        .register("TestMetrics", "Testing metrics", new TestMetrics("1"));

    testMetrics.numBucketCreateFails.incr();
    metrics.publishMetricsNow();

    metrics.unregisterSource("TestMetrics");
    testMetrics = metrics
        .register("TestMetrics", "Testing metrics", new TestMetrics("2"));

    testMetrics.numBucketCreateFails.incr();
    metrics.publishMetricsNow();

    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(stream, UTF_8);

    //WHEN
    sink.writeMetrics(writer);
    writer.flush();

    //THEN
    String writtenMetrics = stream.toString(UTF_8.name());
    System.out.println(writtenMetrics);
    Assert.assertFalse(
        "The first metric should not exist after flushing",
        writtenMetrics.contains(
            "test_metrics_num_bucket_create_fails{context=\"dfs\",testtag=\"testTagValue1\"")
    );
    Assert.assertTrue(
        "The expected metric line is missing from prometheus metrics output",
        writtenMetrics.contains(
            "test_metrics_num_bucket_create_fails{context=\"dfs\",testtag=\"testTagValue2\"")
    );

    metrics.unregisterSource("TestMetrics");
    private String id;

    TestMetrics() {
      this("1");
    }

    TestMetrics(String id) {
      this.id = id;
    }

    @Metric(value={"testTag", ""}, type=Type.TAG)
    String testTag1() {
      return "testTagValue" + id;
    }
