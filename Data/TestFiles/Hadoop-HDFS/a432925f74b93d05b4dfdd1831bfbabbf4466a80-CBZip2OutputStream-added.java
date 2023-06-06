import org.apache.hadoop.classification.VisibleForTesting;
    this.allowableBlockSize = getAllowableBlockSize(this.blockSize100k);
  @VisibleForTesting
  static int getAllowableBlockSize(int blockSize100k) {
    /* 20 is just a paranoia constant */
    return (blockSize100k * BZip2Constants.baseBlockSize) - 20;
  }
