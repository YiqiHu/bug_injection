      clientPrincipal = SecurityUtil.getClientPrincipal(protocol, conf);
      try {
        if (clientPrincipal != null) {
          clientPrincipal =
              SecurityUtil.getServerPrincipal(clientPrincipal, addr);
      } catch (IOException e) {
        throw (AuthorizationException) new AuthorizationException(
            "Can't figure out Kerberos principal name for connection from "
                + addr + " for user=" + user + " protocol=" + protocol)
            .initCause(e);

