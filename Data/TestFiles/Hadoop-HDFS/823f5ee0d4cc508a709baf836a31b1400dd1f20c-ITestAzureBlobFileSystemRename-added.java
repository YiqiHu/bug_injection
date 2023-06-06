import org.apache.hadoop.fs.statistics.IOStatisticAssertions;
import org.apache.hadoop.fs.statistics.IOStatistics;
import static org.apache.hadoop.fs.azurebfs.AbfsStatistic.RENAME_PATH_ATTEMPTS;
import static org.apache.hadoop.fs.contract.ContractTestUtils.dataset;
import static org.apache.hadoop.fs.contract.ContractTestUtils.writeDataset;
  @Test
  public void testRenameWithNoDestinationParentDir() throws Exception {
    describe("Verifying the expected behaviour of ABFS rename when "
        + "destination parent Dir doesn't exist.");

    final AzureBlobFileSystem fs = getFileSystem();
    Path sourcePath = path(getMethodName());
    Path destPath = new Path("falseParent", "someChildFile");

    byte[] data = dataset(1024, 'a', 'z');
    writeDataset(fs, sourcePath, data, data.length, 1024, true);

    // Verify that renaming on a destination with no parent dir wasn't
    // successful.
    assertFalse("Rename result expected to be false with no Parent dir",
        fs.rename(sourcePath, destPath));

    // Verify that metadata was in an incomplete state after the rename
    // failure, and we retired the rename once more.
    IOStatistics ioStatistics = fs.getIOStatistics();
    IOStatisticAssertions.assertThatStatisticCounter(ioStatistics,
        RENAME_PATH_ATTEMPTS.getStatName())
        .describedAs("There should be 2 rename attempts if metadata "
            + "incomplete state failure is hit")
        .isEqualTo(2);
  }
