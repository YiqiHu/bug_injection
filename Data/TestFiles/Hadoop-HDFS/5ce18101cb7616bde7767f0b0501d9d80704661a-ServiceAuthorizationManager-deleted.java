import org.apache.hadoop.security.KerberosInfo;
      KerberosInfo krbInfo = SecurityUtil.getKerberosInfo(protocol, conf);
      if (krbInfo != null) {
        String clientKey = krbInfo.clientPrincipal();
        if (clientKey != null && !clientKey.isEmpty()) {
          try {
            clientPrincipal = SecurityUtil.getServerPrincipal(
                conf.get(clientKey), addr);
          } catch (IOException e) {
            throw (AuthorizationException) new AuthorizationException(
                "Can't figure out Kerberos principal name for connection from "
                + addr + " for user=" + user + " protocol=" + protocol)
                .initCause(e);
          }
