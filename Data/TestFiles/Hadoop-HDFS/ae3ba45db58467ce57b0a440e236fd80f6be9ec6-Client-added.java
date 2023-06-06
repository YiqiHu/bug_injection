        if (socket != null || shouldCloseConnection.get()) {
          setFallBackToSimpleAuth(fallbackToSimpleAuth);
          return;
        }
        UserGroupInformation ticket = remoteId.getTicket();
        if (ticket != null) {
          final UserGroupInformation realUser = ticket.getRealUser();
          if (realUser != null) {
            ticket = realUser;
          }
        }
            setFallBackToSimpleAuth(fallbackToSimpleAuth);

    private void setFallBackToSimpleAuth(AtomicBoolean fallbackToSimpleAuth)
        throws AccessControlException {
      if (authMethod == null || authProtocol != AuthProtocol.SASL) {
        if (authProtocol == AuthProtocol.SASL) {
          LOG.trace("Auth method is not set, yield from setting auth fallback.");
        }
        return;
      }
      if (fallbackToSimpleAuth == null) {
        // this should happen only during testing.
        LOG.trace("Connection {} will skip to set fallbackToSimpleAuth as it is null.", remoteId);
      } else {
        if (fallbackToSimpleAuth.get()) {
          // we already set the value to true, we do not need to examine again.
          return;
        }
      }
      if (authMethod != AuthMethod.SIMPLE) {
        if (fallbackToSimpleAuth != null) {
          LOG.trace("Disabling fallbackToSimpleAuth, target does not use SIMPLE authentication.");
          fallbackToSimpleAuth.set(false);
        }
      } else if (UserGroupInformation.isSecurityEnabled()) {
        if (!fallbackAllowed) {
          throw new AccessControlException("Server asks us to fall back to SIMPLE auth, but this "
              + "client is configured to only allow secure connections.");
        }
        if (fallbackToSimpleAuth != null) {
          LOG.trace("Enabling fallbackToSimpleAuth for target, as we are allowed to fall back.");
          fallbackToSimpleAuth.set(true);
        }
      }
    }

