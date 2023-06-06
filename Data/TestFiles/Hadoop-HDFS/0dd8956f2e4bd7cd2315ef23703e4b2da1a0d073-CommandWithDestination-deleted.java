        targetFs.deleteOnExit(tempTarget.path);
        out = create(target, lazyPersist, direct);
      } catch (IOException e) {
        // failure: clean up if we got as far as creating the file
        if (!direct && out != null) {
          try {
            fs.delete(target.path, false);
          } catch (IOException ignored) {
          }
        }
        throw e;
    FSDataOutputStream create(PathData item, boolean lazyPersist,
        boolean direct)
