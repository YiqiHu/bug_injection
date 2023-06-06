import org.assertj.core.api.Assertions;
  @Test
  public void testSequenceFileWriter() throws Exception {
    Configuration conf = new Configuration();
    // This test only works with Raw File System and not Local File System
    FileSystem fs = FileSystem.getLocal(conf).getRaw();
    Path p = new Path(GenericTestUtils
      .getTempPath("testSequenceFileWriter.seq"));
    try(SequenceFile.Writer writer = SequenceFile.createWriter(
            fs, conf, p, LongWritable.class, Text.class)) {
      Assertions.assertThat(writer.hasCapability
        (StreamCapabilities.HSYNC)).isEqualTo(true);
      Assertions.assertThat(writer.hasCapability(
        StreamCapabilities.HFLUSH)).isEqualTo(true);
      LongWritable key = new LongWritable();
      key.set(1);
      Text value = new Text();
      value.set("somevalue");
      writer.append(key, value);
      writer.flush();
      writer.hflush();
      writer.hsync();
      Assertions.assertThat(fs.getFileStatus(p).getLen()).isGreaterThan(0);
    }
  }

