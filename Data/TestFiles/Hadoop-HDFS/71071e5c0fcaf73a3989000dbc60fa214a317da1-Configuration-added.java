
    loadProps(properties, resources.size() - 1, false);
      loadProps(properties, 0, true);
    }
    return properties;
  }
  /**
   * Loads the resource at a given index into the properties.
   * @param props the object containing the loaded properties.
   * @param startIdx the index where the new resource has been added.
   * @param fullReload flag whether we do complete reload of the conf instead
   *                   of just loading the new resource.
   */
  private synchronized void loadProps(final Properties props,
      final int startIdx, final boolean fullReload) {
    if (props != null) {
      Map<String, String[]> backup =
          updatingResource != null
              ? new ConcurrentHashMap<>(updatingResource) : null;
      loadResources(props, resources, startIdx, fullReload, quietmode);
        props.putAll(overlay);
                             int startIdx,
                             boolean fullReload,
    if(loadDefaults && fullReload) {
    for (int i = startIdx; i < resources.size(); i++) {
