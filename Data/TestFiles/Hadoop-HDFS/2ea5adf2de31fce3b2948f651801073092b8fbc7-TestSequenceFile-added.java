    Path parentDir = new Path(GenericTestUtils.getTempPath(
            "recursiveCreateDir"));
    Path name = new Path(parentDir, "file");
    try {
      createParent = true;
      SequenceFile.createWriter(fs, conf, name, RandomDatum.class,
          RandomDatum.class, 512, (short) 1, 4096, createParent,
          CompressionType.NONE, null, new Metadata());
      // should succeed, fails if exception thrown
    } finally {
      fs.deleteOnExit(parentDir);
      fs.close();
    }
