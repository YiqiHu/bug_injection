import static org.junit.Assert.assertNotEquals;

  @Test
  public void testLazyPersistDirectOverwrite() throws Exception {
    Path testRoot = new Path(testRootDir, "testLazyPersistDirectOverwrite");
    try {
      lfs.delete(testRoot, true);
      lfs.mkdirs(testRoot);
      Path filePath = new Path(testRoot, new Path("srcFile"));
      lfs.create(filePath).close();
      // Put with overwrite in direct mode.
      String[] argv =
          new String[] {"-put", "-f", "-l", "-d", filePath.toString(),
              filePath.toString()};
      assertEquals(0, shell.run(argv));

      // Put without overwrite in direct mode shouldn't be success.
      argv = new String[] {"-put", "-l", "-d", filePath.toString(),
          filePath.toString()};
      assertNotEquals(0, shell.run(argv));
    } finally {
      lfs.delete(testRoot, true);
    }
  }
