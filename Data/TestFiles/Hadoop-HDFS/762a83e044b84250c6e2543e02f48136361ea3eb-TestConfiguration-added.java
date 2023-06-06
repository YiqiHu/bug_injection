  /**
   * Verify that when a configuration is restricted, environment
   * variables and system properties will be unresolved.
   * The fallback patterns for the variables are still parsed.
   */
  @Test
  public void testRestrictedEnv() throws IOException {
    // this test relies on env.PATH being set on all platforms a
    // test run will take place on, and the java.version sysprop
    // set in all JVMs.
    // Restricted configurations will not get access to these values, so
    // will either be unresolved or, for env vars with fallbacks: the fallback
    // values.

    conf.setRestrictSystemProperties(true);

    out = new BufferedWriter(new FileWriter(CONFIG));
    startConfig();
    // a simple property to reference
    declareProperty("d", "D", "D");

    // system property evaluation stops working completely
    declareProperty("system1", "${java.version}", "${java.version}");

    // the env variable does not resolve
    declareProperty("secret1", "${env.PATH}", "${env.PATH}");

    // but all the fallback options do work
    declareProperty("secret2", "${env.PATH-a}", "a");
    declareProperty("secret3", "${env.PATH:-b}", "b");
    declareProperty("secret4", "${env.PATH:-}", "");
    declareProperty("secret5", "${env.PATH-}", "");
    // special case
    declareProperty("secret6", "${env.PATH:}", "${env.PATH:}");
    // safety check
    declareProperty("secret7", "${env.PATH:--}", "-");

    // recursive eval of the fallback
    declareProperty("secret8", "${env.PATH:-${d}}", "D");

    // if the fallback doesn't resolve, the result is the whole variable raw.
    declareProperty("secret9", "${env.PATH:-$d}}", "${env.PATH:-$d}}");

    endConfig();
    Path fileResource = new Path(CONFIG);
    conf.addResource(fileResource);

    for (Prop p : props) {
      System.out.println("p=" + p.name);
      String gotVal = conf.get(p.name);
      String gotRawVal = conf.getRaw(p.name);
      assertEq(p.val, gotRawVal);
      assertEq(p.expectEval, gotVal);
    }
  }

