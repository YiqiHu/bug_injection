  private static final String SSL_SERVER_KEYSTORE_PROP_PREFIX = "ssl.server" +
          ".keystore";
  private static final String SSL_SERVER_TRUSTSTORE_PROP_PREFIX = "ssl.server" +
          ".truststore";
  private static final String SERVLET_NAME_LONGHEADER = "longheader";
  private static final String SERVLET_PATH_LONGHEADER =
  private static final String SERVLET_NAME_ECHO = "echo";
  private static final String SERVLET_PATH_ECHO = "/" + SERVLET_NAME_ECHO;
  private static final String EXCLUDED_CIPHERS =
  private static final String INCLUDED_PROTOCOLS = "SSLv2Hello,TLSv1.1";
  private static void storeHttpsCipherSuites() {
  private static void restoreHttpsCipherSuites() {
  private static void turnOnSSLDebugLogging() {
  private static void restoreSSLDebugLogging() {
