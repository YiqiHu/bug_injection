import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  Logger TOKEN_LOG = LoggerFactory.getLogger(DelegationTokenIssuer.class);
    if (TOKEN_LOG.isDebugEnabled()) {
      TOKEN_LOG.debug("Search token for service {} in credentials",
          serviceName);
    }
        if (TOKEN_LOG.isDebugEnabled()) {
          TOKEN_LOG.debug("Token for service {} not found in credentials," +
              " try getDelegationToken.", serviceName);
        }
      } else {
        if (TOKEN_LOG.isDebugEnabled()) {
          TOKEN_LOG.debug("Token for service {} found in credentials," +
              "skip getDelegationToken.", serviceName);
        }
