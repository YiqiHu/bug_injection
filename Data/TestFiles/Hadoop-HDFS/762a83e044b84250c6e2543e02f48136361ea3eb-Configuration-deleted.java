      if (!restrictSystemProps) {
        try {
          if (var.startsWith("env.") && 4 < var.length()) {
            String v = var.substring(4);
            int i = 0;
            for (; i < v.length(); i++) {
              char c = v.charAt(i);
              if (c == ':' && i < v.length() - 1 && v.charAt(i + 1) == '-') {
                val = getenv(v.substring(0, i));
                if (val == null || val.length() == 0) {
                  val = v.substring(i + 2);
                }
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

    return System.getenv(name);
    return System.getProperty(key);
