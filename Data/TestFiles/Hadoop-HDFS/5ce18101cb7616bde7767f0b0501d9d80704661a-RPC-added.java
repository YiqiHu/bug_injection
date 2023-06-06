import org.apache.hadoop.security.SecurityUtil;
      String client = SecurityUtil.getClientPrincipal(protocolClass, getConf());
      if (client != null) {
        // notify the server's rpc scheduler that the protocol user has
        // highest priority.  the scheduler should exempt the user from
        // priority calculations.
        try {
          setPriorityLevel(UserGroupInformation.createRemoteUser(client), -1);
        } catch (Exception ex) {
          LOG.warn("Failed to set scheduling priority for " + client, ex);
        }
      }
    }
