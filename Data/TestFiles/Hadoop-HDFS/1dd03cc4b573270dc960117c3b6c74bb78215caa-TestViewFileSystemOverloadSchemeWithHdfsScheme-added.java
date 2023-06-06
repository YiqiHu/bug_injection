        FileSystem fs = FileSystem.get(conf);
        fs.resolvePath(new Path(userFolder));
  @Test(expected = IOException.class, timeout = 30000)
