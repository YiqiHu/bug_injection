
  /**
   * Look up the client principal for a given protocol. It searches all known
   * SecurityInfo providers.
   * @param protocol the protocol class to get the information for
   * @param conf configuration object
   * @return client principal or null if it has no client principal defined.
   */
  public static String getClientPrincipal(Class<?> protocol,
      Configuration conf) {
    String user = null;
    KerberosInfo krbInfo = SecurityUtil.getKerberosInfo(protocol, conf);
    if (krbInfo != null) {
      String key = krbInfo.clientPrincipal();
      user = (key != null && !key.isEmpty()) ? conf.get(key) : null;
    }
    return user;
  }

