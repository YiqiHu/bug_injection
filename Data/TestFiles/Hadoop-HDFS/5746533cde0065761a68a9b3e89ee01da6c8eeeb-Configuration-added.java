    else {
      LOG.warn("Invalid value for boolean: " + valueString +
               ", choose default value: " + defaultValue + " for " + name);
      return defaultValue;
    }
