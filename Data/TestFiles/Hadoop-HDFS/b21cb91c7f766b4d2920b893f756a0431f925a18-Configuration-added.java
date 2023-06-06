    List<String> resultKeys = new ArrayList<>();
          resultKeys.add((String) item.getKey());
    resultKeys.forEach(item ->
            result.put(item, substituteVars(getProps().getProperty(item))));
