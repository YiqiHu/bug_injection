
  @Test
  public void testInvalidMountPoints() throws Exception {
    final String clusterName = "cluster" + new Random().nextInt();
    Configuration config = new Configuration(conf);
    config.set(ConfigUtil.getConfigViewFsPrefix(clusterName) + "." +
        Constants.CONFIG_VIEWFS_LINK + "." + "/invalidPath",
        "othermockfs:|mockauth/mockpath");

    try {
      FileSystem viewFs = FileSystem.get(
          new URI("viewfs://" + clusterName + "/"), config);
      fail("FileSystem should not initialize. Should fail with IOException");
    } catch (IOException ex) {
      assertTrue("Should get URISyntax Exception",
          ex.getMessage().startsWith("URISyntax exception"));
    }
  }
