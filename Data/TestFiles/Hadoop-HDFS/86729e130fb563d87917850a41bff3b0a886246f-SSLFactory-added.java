import static org.apache.hadoop.util.PlatformName.JAVA_VENDOR_NAME;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
  public static final String KEY_MANAGER_SSLCERTIFICATE =
      JAVA_VENDOR_NAME.contains("IBM") ? "ibmX509" :
          KeyManagerFactory.getDefaultAlgorithm();

  public static final String TRUST_MANAGER_SSLCERTIFICATE =
      JAVA_VENDOR_NAME.contains("IBM") ? "ibmX509" :
          TrustManagerFactory.getDefaultAlgorithm();
