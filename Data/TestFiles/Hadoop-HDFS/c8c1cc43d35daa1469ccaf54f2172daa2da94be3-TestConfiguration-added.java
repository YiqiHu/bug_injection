  @Test
  public void testRelativeIncludesWithLoadingViaUri() throws Exception {
    tearDown();
    File configFile = new File("./tmp/test-config.xml");
    File configFile2 = new File("./tmp/test-config2.xml");

    new File(configFile.getParent()).mkdirs();
    out = new BufferedWriter(new FileWriter(configFile2));
    startConfig();
    appendProperty("a", "b");
    endConfig();

    out = new BufferedWriter(new FileWriter(configFile));
    startConfig();
    // Add the relative path instead of the absolute one.
    startInclude(configFile2.getName());
    endInclude();
    appendProperty("c", "d");
    endConfig();

    // verify that the includes file contains all properties
    Path fileResource = new Path(configFile.toURI());
    conf.addResource(fileResource);
    assertEquals("b", conf.get("a"));
    assertEquals("d", conf.get("c"));

    // Cleanup
    configFile.delete();
    configFile2.delete();
    new File(configFile.getParent()).delete();
  }

