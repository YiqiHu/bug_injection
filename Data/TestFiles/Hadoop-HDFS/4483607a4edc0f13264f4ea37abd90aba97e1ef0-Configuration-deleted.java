    Map<String,String> result = new HashMap<String,String>();
    for(Map.Entry<Object,Object> item: getProps().entrySet()) {
      if (item.getKey() instanceof String &&
          item.getValue() instanceof String) {
