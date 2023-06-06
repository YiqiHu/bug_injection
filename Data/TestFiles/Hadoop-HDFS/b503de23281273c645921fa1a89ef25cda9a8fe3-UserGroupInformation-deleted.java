      UserGroupInformation ugi) throws IOException {
      for (Token<?> token : ugi.getTokens()) {
        log.debug("+token:" + token);
