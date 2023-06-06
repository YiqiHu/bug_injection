import org.apache.hadoop.fs.s3a.commit.files.SuccessData;
   * @return the path written to
  protected Path writeTextOutput(TaskAttemptContext context)
      LoggingTextOutputFormat.LoggingLineRecordWriter<Object, Object>
          recordWriter = new LoggingTextOutputFormat<>().getRecordWriter(
      writeOutput(recordWriter,
          context);
      return recordWriter.getDest();
    setupCommitter(committer, tContext);
    describe("setup complete\n");
  }

  private void setupCommitter(
      final AbstractS3ACommitter committer,
      final TaskAttemptContext tContext) throws IOException {
  /**
   * HADOOP-17258. If a second task attempt is committed, it
   * must succeed, and the output of the first TA, even if already
   * committed, MUST NOT be visible in the final output.
   * <p></p>
   * What's important is not just that only one TA must succeed,
   * but it must be the last one executed. Why? because that's
   * the one
   */
  @Test
  public void testTwoTaskAttemptsCommit() throws Exception {
    describe("Commit two task attempts;" +
        " expect the second attempt to succeed.");
    JobData jobData = startJob(false);
    JobContext jContext = jobData.jContext;
    TaskAttemptContext tContext = jobData.tContext;
    AbstractS3ACommitter committer = jobData.committer;
    // do commit
    describe("\ncommitting task");
    // write output for TA 1,
    Path outputTA1 = writeTextOutput(tContext);

    // speculatively execute committer 2.

    // jobconf with a different base to its parts.
    Configuration conf2 = jobData.conf;
    conf2.set("mapreduce.output.basename", "attempt2");
    String attempt2 = "attempt_" + jobId + "_m_000000_1";
    TaskAttemptID ta2 = TaskAttemptID.forName(attempt2);
    TaskAttemptContext tContext2 = new TaskAttemptContextImpl(
        conf2, ta2);

    AbstractS3ACommitter committer2 = standardCommitterFactory
        .createCommitter(tContext2);
    setupCommitter(committer2, tContext2);
    // write output for TA 2,
    Path outputTA2 = writeTextOutput(tContext2);

    // verify the names are different.
    String name1 = outputTA1.getName();
    String name2 = outputTA2.getName();
    Assertions.assertThat(name1)
        .describedAs("name of task attempt output %s", outputTA1)
        .isNotEqualTo(name2);

    // commit task 1
    committer.commitTask(tContext);

    // then pretend that task1 didn't respond, so
    // commit task 2
    committer2.commitTask(tContext2);

    // and the job
    committer2.commitJob(tContext);

    // validate output
    S3AFileSystem fs = getFileSystem();
    SuccessData successData = validateSuccessFile(outDir, "", fs, "query", 1);
    Assertions.assertThat(successData.getFilenames())
        .describedAs("Files committed")
        .hasSize(1);

    assertPathExists("attempt2 output", new Path(outDir, name2));
    assertPathDoesNotExist("attempt1 output", new Path(outDir, name1));

    assertNoMultipartUploadsPending(outDir);
  }

