      protected AbstractFileSystem getTargetFileSystem(final URI uri)
        throws URISyntaxException, UnsupportedFileSystemException {
          String pathString = uri.getPath();
          if (pathString.isEmpty()) {
            pathString = "/";
          return new ChRootedFs(
              AbstractFileSystem.createFileSystem(uri, config),
              new Path(pathString));
        mountPoints.get(i).target.targetFileSystem.getDelegationTokens(renewer);
