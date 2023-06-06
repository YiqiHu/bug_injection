    Properties props = getProps();
    Map<String, String> result = new HashMap<>();
    synchronized (props) {
      for (Map.Entry<Object, Object> item : props.entrySet()) {
        if (item.getKey() instanceof String && item.getValue() instanceof String) {
        }
