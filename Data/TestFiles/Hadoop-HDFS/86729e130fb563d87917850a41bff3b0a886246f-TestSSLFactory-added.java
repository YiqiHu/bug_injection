import static java.security.Security.getProperty;
import static java.security.Security.setProperty;
  @Test
  public void testDifferentAlgorithm() throws Exception {
    Configuration conf = createConfiguration(false, true);
    String currAlg = getProperty("ssl.KeyManagerFactory.algorithm");
    setProperty("ssl.KeyManagerFactory.algorithm", "PKIX");
    SSLFactory sslFactory = new SSLFactory(SSLFactory.Mode.CLIENT, conf);
    try {
      sslFactory.init();
    } finally {
      sslFactory.destroy();
      setProperty("ssl.KeyManagerFactory.algorithm", currAlg);
    }
  }

