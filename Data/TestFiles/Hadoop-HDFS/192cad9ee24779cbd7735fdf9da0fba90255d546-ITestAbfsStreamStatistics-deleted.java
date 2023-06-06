      Testing if 2 read_ops value is coming after reading full content from a
      file (3 if anything to read from Buffer too).
      Reason: read() call gives read_ops=1,
      reading from AbfsClient(http GET) gives read_ops=2.
      assertReadWriteOps("read", 2, statistics.getReadOps());
