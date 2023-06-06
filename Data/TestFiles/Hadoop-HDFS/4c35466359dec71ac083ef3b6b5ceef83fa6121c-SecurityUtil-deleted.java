      Name[] names = resolverConfig.searchPath();
      if (names != null) {
        for (Name name : names) {
          searchDomains.add(name.toString());
        }
