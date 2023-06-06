    /**
     * S3A path.
     * added in HADOOP-18330
     */
    private URI pathUri;


    /**
     * Get the full s3 path.
     * added in HADOOP-18330
     * @return path URI
     */
    public URI getPathUri() {
      return pathUri;
    }

    /**
     * Set full s3a path.
     * added in HADOOP-18330
     * @param value new value
     * @return the builder
     */
    public S3ClientCreationParameters withPathUri(
        final URI value) {
      pathUri = value;
      return this;
    }
