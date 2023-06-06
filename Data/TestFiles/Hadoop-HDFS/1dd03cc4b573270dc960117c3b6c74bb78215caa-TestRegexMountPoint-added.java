import java.util.function.Function;
      protected Function<URI, TestRegexMountPointFileSystem>
          initAndGetTargetFs() {
        return new Function<URI, TestRegexMountPointFileSystem>() {
          @Override
          public TestRegexMountPointFileSystem apply(URI uri) {
            return new TestRegexMountPointFileSystem(uri);
          }
        };
