  public final static String SERVER_KEY_STORE_PASSWORD_DEFAULT = "serverP";
  public final static String CLIENT_KEY_STORE_PASSWORD_DEFAULT = "clientP";
  public final static String TRUST_STORE_PASSWORD_DEFAULT = "trustP";

  /**
   * Performs complete setup of SSL configuration in preparation for testing an
   * SSLFactory.  This includes keys, certs, keystores, truststores, the server
   * SSL configuration file, the client SSL configuration file, and the master
   * configuration file read by the SSLFactory.
   *
   * @param keystoresDir
   * @param sslConfDir
   * @param conf
   * @param useClientCert
   * @param trustStore
   * @param excludeCiphers
   * @throws Exception
   */
  public static void setupSSLConfig(String keystoresDir, String sslConfDir,
      Configuration conf, boolean useClientCert, boolean trustStore,
      String excludeCiphers) throws Exception {
    setupSSLConfig(keystoresDir, sslConfDir, conf, useClientCert, trustStore,
        excludeCiphers, SERVER_KEY_STORE_PASSWORD_DEFAULT,
        CLIENT_KEY_STORE_PASSWORD_DEFAULT, TRUST_STORE_PASSWORD_DEFAULT);
  }


  /**
   * Performs complete setup of SSL configuration in preparation for testing an
   * SSLFactory.  This includes keys, certs, keystores, truststores, the server
   * SSL configuration file, the client SSL configuration file, and the master
   * configuration file read by the SSLFactory and the passwords required to
   * access the keyStores (Server and Client KeyStore Passwords and
   * TrustStore Password).
   *
   * @param keystoresDir
   * @param sslConfDir
   * @param conf
   * @param useClientCert
   * @param trustStore
   * @param excludeCiphers
   * @param serverPassword
   * @param clientPassword
   * @param trustPassword
   * @throws Exception
   */
  @SuppressWarnings("checkstyle:parameternumber")
  public static void setupSSLConfig(String keystoresDir, String sslConfDir,
      Configuration conf, boolean useClientCert, boolean trustStore,
      String excludeCiphers, String serverPassword, String clientPassword,
      String trustPassword) throws Exception {

    Configuration clientSSLConf = createClientSSLConfig(clientKS,
        clientPassword, clientPassword, trustKS, trustPassword, excludeCiphers);
    Configuration serverSSLConf = createServerSSLConfig(serverKS,
        serverPassword, serverPassword, trustKS, trustPassword, excludeCiphers);
      String password, String keyPassword, String trustKS,
      String trustPassword) {
      clientKS, password, keyPassword, trustKS, trustPassword, "");
  public static Configuration createClientSSLConfig(String clientKS,
      String password, String keyPassword, String trustKS,
      String trustPassword, String excludeCiphers) {
      clientKS, password, keyPassword, trustKS, trustPassword, excludeCiphers);
      String password, String keyPassword, String trustKS, String trustPassword)
      throws IOException {
      serverKS, password, keyPassword, trustKS, trustPassword, "");
  public static Configuration createServerSSLConfig(String serverKS,
      String password, String keyPassword, String trustKS, String trustPassword,
      String excludeCiphers) throws IOException {
      serverKS, password, keyPassword, trustKS, trustPassword, excludeCiphers);
      String keystore, String password, String keyPassword, String trustKS,
      String trustStorePwd, String excludeCiphers) {
    if (trustStorePwd != null) {
          trustStorePwd);
