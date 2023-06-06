  /**
   * Checks if variable substitution is accessible via a public API.
   */
  @Test
  public void testCommonVariableSubstitution() {
    conf.set("intvar", String.valueOf(42));
    String intVar = conf.substituteCommonVariables("${intvar}");

    assertEquals("42", intVar);
  }

