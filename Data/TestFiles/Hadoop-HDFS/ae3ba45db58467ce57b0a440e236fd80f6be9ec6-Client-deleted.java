      if (socket != null || shouldCloseConnection.get()) {
        return;
      }
      UserGroupInformation ticket = remoteId.getTicket();
      if (ticket != null) {
        final UserGroupInformation realUser = ticket.getRealUser();
        if (realUser != null) {
          ticket = realUser;
        }
      }
              if (fallbackToSimpleAuth != null) {
                fallbackToSimpleAuth.set(false);
              }
            } else if (UserGroupInformation.isSecurityEnabled()) {
              if (!fallbackAllowed) {
                throw new AccessControlException(
                    "Server asks us to fall back to SIMPLE " +
                    "auth, but this client is configured to only allow secure " +
                    "connections.");
              }
              if (fallbackToSimpleAuth != null) {
                fallbackToSimpleAuth.set(true);
              }

