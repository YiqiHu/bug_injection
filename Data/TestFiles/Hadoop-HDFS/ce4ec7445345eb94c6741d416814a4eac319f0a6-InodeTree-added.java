    private INodeLink<T> fallbackLink = null;
    INodeLink<T> getFallbackLink() {
      return fallbackLink;
    }

    void addFallbackLink(INodeLink<T> link) throws IOException {
      if (!isRoot) {
        throw new IOException("Fallback link can only be added for root");
      }
      this.fallbackLink = link;
    }

      getRootDir().addFallbackLink(rootFallbackLink);
