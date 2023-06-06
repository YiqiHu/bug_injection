        out = create(target, lazyPersist);
        if (!direct) {
          deleteOnExit(target.path);
        }
    FSDataOutputStream create(PathData item, boolean lazyPersist)
