  /**
   * Test the config value separated by delimiter
   */
  @Test public void testDelimiterConf() {
    String filename = getTestFilename("test-metrics2-delimiter");
    new ConfigBuilder().add("p1.foo", "p1foo1,p1foo2,p1foo3").save(filename);

    MetricsConfig mc = MetricsConfig.create("p1", filename);
    Configuration expected = new ConfigBuilder()
        .add("foo", "p1foo1")
        .add("foo", "p1foo2")
        .add("foo", "p1foo3")
        .config;
    assertEq(expected, mc);
  }

