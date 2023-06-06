  @Test
  public void testDoFilterNotAuthenticatedLowerCase() throws Exception {
    AuthenticationFilter filter = new AuthenticationFilter();
    try {
      FilterConfig config = Mockito.mock(FilterConfig.class);
      Mockito.when(config.getInitParameter("management.operation.return")).
              thenReturn("true");
      Mockito.when(config.getInitParameter(AuthenticationFilter.AUTH_TYPE)).thenReturn(
              DummyAuthenticationHandler.class.getName());
      Mockito.when(config.getInitParameterNames()).thenReturn(
              new Vector<>(
                      Arrays.asList(AuthenticationFilter.AUTH_TYPE,
                              "management.operation.return")).elements());
      getMockedServletContextWithStringSigner(config);
      filter.init(config);

      HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
      Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://foo:8080/bar"));

      HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

      FilterChain chain = Mockito.mock(FilterChain.class);

      Mockito.doAnswer((Answer<Object>) invocation -> {
        Assert.fail();
        return null;
      }).when(chain).doFilter(any(), any());

      Mockito.when(response.containsHeader("www-authenticate")).thenReturn(true);
      filter.doFilter(request, response, chain);

      Mockito.verify(response).sendError(
              HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
    } finally {
      filter.destroy();
    }
  }

