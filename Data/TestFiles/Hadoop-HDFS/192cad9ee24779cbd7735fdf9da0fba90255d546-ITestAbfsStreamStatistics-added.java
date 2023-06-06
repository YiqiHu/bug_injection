       * Testing if 2 read_ops value is coming after reading full content
       * from a file (3 if anything to read from Buffer too). Reason: read()
       * call gives read_ops=1, reading from AbfsClient(http GET) gives
       * read_ops=2.
       *
       * In some cases ABFS-prefetch thread runs in the background which
       * returns some bytes from buffer and gives an extra readOp.
       * Thus, making readOps values arbitrary and giving intermittent
       * failures in some cases. Hence, readOps values of 2 or 3 is seen in
       * different setups.
       *
      assertTrue(String.format("The actual value of %d was not equal to the "
              + "expected value of 2 or 3", statistics.getReadOps()),
          statistics.getReadOps() == 2 || statistics.getReadOps() == 3);
