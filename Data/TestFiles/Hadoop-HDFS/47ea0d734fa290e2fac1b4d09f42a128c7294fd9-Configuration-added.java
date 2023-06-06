  /**
   * Provides a public wrapper over substituteVars in order to avoid compatibility issues.
   * See HADOOP-18021 for further details.
   *
   * @param expr the literal value of a config key
   * @return null if expr is null, otherwise the value resulting from expanding
   * expr using the algorithm above.
   * @throws IllegalArgumentException when more than
   * {@link Configuration#MAX_SUBST} replacements are required
   */
  public String substituteCommonVariables(String expr) {
    return substituteVars(expr);
  }

   * In order not to introduce breaking changes (as Oozie for example contains a method with the
   * same name and same signature) do not make this method public, use substituteCommonVariables
   * in this case.
   *
  private String substituteVars(String expr) {
