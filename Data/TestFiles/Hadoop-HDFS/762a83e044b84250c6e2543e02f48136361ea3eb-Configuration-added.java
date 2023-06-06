      try {
        // evaluate system properties or environment variables even when
        // the configuration is restricted -the restrictions are enforced
        // in the getenv/getProperty calls
        if (var.startsWith("env.") && 4 < var.length()) {
          String v = var.substring(4);
          int i = 0;
          for (; i < v.length(); i++) {
            char c = v.charAt(i);
            if (c == ':' && i < v.length() - 1 && v.charAt(i + 1) == '-') {
              val = getenv(v.substring(0, i));
              if (val == null || val.length() == 0) {
                val = v.substring(i + 2);
              break;
            } else if (c == '-') {
              val = getenv(v.substring(0, i));
              if (val == null) {
                val = v.substring(i + 1);
              }
              break;
          if (i == v.length()) {
            val = getenv(v);
          }
        } else {
          val = getProperty(var);
      } catch (SecurityException se) {
        LOG.warn("Unexpected SecurityException in Configuration", se);

  /**
   * Get the environment variable value if
   * {@link #restrictSystemProps} does not block this.
   * @param name environment variable name.
   * @return the value or null if either it is unset or access forbidden.
   */
    if (!restrictSystemProps) {
      return System.getenv(name);
    } else {
      return null;
    }
  /**
   * Get a system property value if
   * {@link #restrictSystemProps} does not block this.
   * @param key property key
   * @return the value or null if either it is unset or access forbidden.
   */
    if (!restrictSystemProps) {
      return System.getProperty(key);
    } else {
      return null;
    }
