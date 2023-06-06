      UserGroupInformation ugi) {
      for (Map.Entry<Text, Token<? extends TokenIdentifier>> kv :
          ugi.getCredentials().getTokenMap().entrySet()) {
        log.debug("+token: {} -> {}", kv.getKey(), kv.getValue());
