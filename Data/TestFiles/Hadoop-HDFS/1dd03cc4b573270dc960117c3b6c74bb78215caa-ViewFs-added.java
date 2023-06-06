import java.util.function.Function;
import java.security.PrivilegedExceptionAction;
      protected Function<URI, AbstractFileSystem> initAndGetTargetFs() {
        return new Function<URI, AbstractFileSystem>() {
          @Override
          public AbstractFileSystem apply(final URI uri) {
            AbstractFileSystem fs;
            try {
              fs = ugi.doAs(
                  new PrivilegedExceptionAction<AbstractFileSystem>() {
                    @Override
                    public AbstractFileSystem run() throws IOException {
                      return AbstractFileSystem.createFileSystem(uri, config);
                    }
                  });
              String pathString = uri.getPath();
              if (pathString.isEmpty()) {
                pathString = "/";
              }
              return new ChRootedFs(fs, new Path(pathString));
            } catch (IOException | URISyntaxException |
                InterruptedException ex) {
              LOG.error("Could not initialize underlying FileSystem object"
                  +" for uri " + uri + "with exception: " + ex.toString());
            }
            return null;
        };
          mountPoints.get(i).target.getTargetFileSystem()
              .getDelegationTokens(renewer);
