
    // Add tokens from fallback FS
    if (this.fsState.getRootFallbackLink() != null) {
      AbstractFileSystem rootFallbackFs =
          this.fsState.getRootFallbackLink().getTargetFileSystem();
      List<Token<?>> tokens = rootFallbackFs.getDelegationTokens(renewer);
      if (tokens != null) {
        result.addAll(tokens);
      }
    }

