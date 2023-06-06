import org.apache.hadoop.io.compress.AlreadyClosedException;
    if (state == BuiltInGzipDecompressor.GzipStateLabel.ENDED) {
      throw new AlreadyClosedException("compress called on closed compressor");
    }


    state = BuiltInGzipDecompressor.GzipStateLabel.ENDED;
