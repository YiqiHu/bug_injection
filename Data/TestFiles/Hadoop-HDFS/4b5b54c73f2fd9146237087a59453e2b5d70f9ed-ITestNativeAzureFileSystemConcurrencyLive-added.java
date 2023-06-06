import org.apache.hadoop.fs.FileStatus;
  /**
   * Validate the bug fix for HADOOP-17089.  Please note that we were never
   * able to reproduce this except during a Spark job that ran for multiple days
   * and in a hacked-up azure-storage SDK that added sleep before and after
   * the call to factory.setNamespaceAware(true) as shown in the description of
   *
   * @see <a href="https://github.com/Azure/azure-storage-java/pull/546">https://github.com/Azure/azure-storage-java/pull/546</a>
   */
  @Test(timeout = TEST_EXECUTION_TIMEOUT)
  public void testConcurrentList() throws Exception {
    final Path testDir = new Path("/tmp/data-loss/11230174258112/_temporary/0/_temporary/attempt_20200624190514_0006_m_0");
    final Path testFile = new Path(testDir, "part-00004-15ea87b1-312c-4fdf-1820-95afb3dfc1c3-a010.snappy.parquet");
    fs.create(testFile).close();
    List<ListTask> tasks = new ArrayList<>(THREAD_COUNT);

    for (int i = 0; i < THREAD_COUNT; i++) {
      tasks.add(new ListTask(fs, testDir));
    }

    ExecutorService es = null;
    try {
      es = Executors.newFixedThreadPool(THREAD_COUNT);

      List<Future<Integer>> futures = es.invokeAll(tasks);

      for (Future<Integer> future : futures) {
        Assert.assertTrue(future.isDone());

        // we are using Callable<V>, so if an exception
        // occurred during the operation, it will be thrown
        // when we call get
        long fileCount = future.get();
        assertEquals("The list should always contain 1 file.", 1, fileCount);
      }
    } finally {
      if (es != null) {
        es.shutdownNow();
      }
    }
  }

    FileSystem getFileSystem() {
    Path getFilePath() {

  class ListTask extends FileSystemTask<Integer> {
    ListTask(FileSystem fs, Path p) {
      super(fs, p);
    }

    public Integer call() throws Exception {
      FileSystem fs = getFileSystem();
      Path p = getFilePath();
      FileStatus[] files = fs.listStatus(p);
      return files.length;
    }
  }
