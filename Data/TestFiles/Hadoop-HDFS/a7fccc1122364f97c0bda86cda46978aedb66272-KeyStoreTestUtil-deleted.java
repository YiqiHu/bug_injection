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
                                    Configuration conf, boolean useClientCert,
      boolean trustStore, String excludeCiphers)
    throws Exception {
    String clientPassword = "clientP";
    String serverPassword = "serverP";
    String trustPassword = "trustP";
    Configuration clientSSLConf = createClientSSLConfig(clientKS, clientPassword,
      clientPassword, trustKS, excludeCiphers);
    Configuration serverSSLConf = createServerSSLConfig(serverKS, serverPassword,
      serverPassword, trustKS, excludeCiphers);
      String password, String keyPassword, String trustKS) {
      clientKS, password, keyPassword, trustKS, "");
    public static Configuration createClientSSLConfig(String clientKS,
      String password, String keyPassword, String trustKS, String excludeCiphers) {
      clientKS, password, keyPassword, trustKS, excludeCiphers);
      String password, String keyPassword, String trustKS) throws IOException {
      serverKS, password, keyPassword, trustKS, "");
    public static Configuration createServerSSLConfig(String serverKS,
      String password, String keyPassword, String trustKS, String excludeCiphers) throws IOException {
      serverKS, password, keyPassword, trustKS, excludeCiphers);
    String keystore, String password, String keyPassword, String trustKS, String excludeCiphers) {
    String trustPassword = "trustP";
    if (trustPassword != null) {
        trustPassword);
