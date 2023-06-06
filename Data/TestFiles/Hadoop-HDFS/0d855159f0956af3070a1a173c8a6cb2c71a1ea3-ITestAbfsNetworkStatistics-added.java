import org.apache.hadoop.fs.FileAlreadyExistsException;
  /**
   * Testing bytes_received counter value when a response failure occurs.
   */
  @Test
  public void testAbfsHttpResponseFailure() throws IOException {
    describe("Test to check the values of bytes received counter when a "
        + "response is failed");

    AzureBlobFileSystem fs = getFileSystem();
    Path responseFailurePath = path(getMethodName());
    Map<String, Long> metricMap;
    FSDataOutputStream out = null;

    try {
      //create an empty file
      out = fs.create(responseFailurePath);
      //Re-creating the file again on same path with false overwrite, this
      // would cause a response failure with status code 409.
      out = fs.create(responseFailurePath, false);
    } catch (FileAlreadyExistsException faee) {
      metricMap = fs.getInstrumentationMap();
      // Assert after catching the 409 error to check the counter values.
      assertAbfsStatistics(AbfsStatistic.BYTES_RECEIVED, 0, metricMap);
    } finally {
      IOUtils.cleanupWithLogger(LOG, out);
    }
  }
