  @Test
  public void testExtractTokenCookieHeader() throws Exception {
    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);

    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

    String tokenStr = "foo";
    Map<String, List<String>> headers = new HashMap<>();
    List<String> cookies = new ArrayList<>();
    cookies.add(AuthenticatedURL.AUTH_COOKIE + "=" + tokenStr);
    headers.put("Set-Cookie", cookies);
    Mockito.when(conn.getHeaderFields()).thenReturn(headers);

    AuthenticatedURL.Token token = new AuthenticatedURL.Token();
    AuthenticatedURL.extractToken(conn, token);

    Assert.assertTrue(token.isSet());
  }

  @Test
  public void testExtractTokenLowerCaseCookieHeader() throws Exception {
    HttpURLConnection conn = Mockito.mock(HttpURLConnection.class);

    Mockito.when(conn.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

    String tokenStr = "foo";
    Map<String, List<String>> headers = new HashMap<>();
    List<String> cookies = new ArrayList<>();
    cookies.add(AuthenticatedURL.AUTH_COOKIE + "=" + tokenStr);
    headers.put("set-cookie", cookies);
    Mockito.when(conn.getHeaderFields()).thenReturn(headers);

    AuthenticatedURL.Token token = new AuthenticatedURL.Token();
    AuthenticatedURL.extractToken(conn, token);

    Assert.assertTrue(token.isSet());
  }

