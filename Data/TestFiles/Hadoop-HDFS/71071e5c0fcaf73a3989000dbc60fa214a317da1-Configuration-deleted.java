
    reloadConfiguration();
      Map<String, String[]> backup = updatingResource != null ?
          new ConcurrentHashMap<String, String[]>(updatingResource) : null;
      loadResources(properties, resources, quietmode);
        properties.putAll(overlay);
    return properties;
    if(loadDefaults) {
    for (int i = 0; i < resources.size(); i++) {
