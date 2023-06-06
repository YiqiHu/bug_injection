      if(sinks.get(name) == null) {
        registerSink(name, description, sink);
      } else {
        LOG.warn("Sink "+ name +" already exists!");
      }
