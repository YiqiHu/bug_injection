  static final String SSL_SERVER_KEYSTORE_PROP_PREFIX = "ssl.server.keystore";
  static final String SSL_SERVER_TRUSTSTORE_PROP_PREFIX = "ssl.server" +
      ".truststore";
  static final String SERVLET_NAME_LONGHEADER = "longheader";
  static final String SERVLET_PATH_LONGHEADER =
  static final String SERVLET_NAME_ECHO = "echo";
  static final String SERVLET_PATH_ECHO = "/" + SERVLET_NAME_ECHO;
  static final String EXCLUDED_CIPHERS =
  static final String INCLUDED_PROTOCOLS = "SSLv2Hello,TLSv1.1";
  static void storeHttpsCipherSuites() {
  static void restoreHttpsCipherSuites() {
  static void turnOnSSLDebugLogging() {
  static void restoreSSLDebugLogging() {
