  protected void writeTextOutput(TaskAttemptContext context)
      writeOutput(new LoggingTextOutputFormat().getRecordWriter(context),
    describe("setup complete\n");
