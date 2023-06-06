    Path name = new Path(new Path(GenericTestUtils.getTempPath(
        "recursiveCreateDir")), "file");
    createParent = true;
    SequenceFile.createWriter(fs, conf, name, RandomDatum.class,
        RandomDatum.class, 512, (short) 1, 4096, createParent,
        CompressionType.NONE, null, new Metadata());
    // should succeed, fails if exception thrown
