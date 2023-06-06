import java.io.File;
import java.io.FileOutputStream;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();


  @Test
  public void testGetContentSummary() throws IOException {
    ContentSummary summaryBefore =
        fsView.getContentSummary(new Path("/internalDir"));
    String expected = "GET CONTENT SUMMARY";
    Path filePath =
        new Path("/internalDir/internalDir2/linkToDir3", "foo");

    try (FSDataOutputStream outputStream = fsView.create(filePath)) {
      outputStream.write(expected.getBytes());
    }

    Path newDirPath = new Path("/internalDir/linkToDir2", "bar");
    fsView.mkdirs(newDirPath);

    ContentSummary summaryAfter =
        fsView.getContentSummary(new Path("/internalDir"));
    assertEquals("The file count didn't match",
        summaryBefore.getFileCount() + 1,
        summaryAfter.getFileCount());
    assertEquals("The size didn't match",
        summaryBefore.getLength() + expected.length(),
        summaryAfter.getLength());
    assertEquals("The directory count didn't match",
        summaryBefore.getDirectoryCount() + 1,
        summaryAfter.getDirectoryCount());
  }

  @Test
  public void testGetContentSummaryWithFileInLocalFS() throws Exception {
    ContentSummary summaryBefore =
        fsView.getContentSummary(new Path("/internalDir"));
    String expected = "GET CONTENT SUMMARY";
    File localFile = temporaryFolder.newFile("localFile");
    try (FileOutputStream fos = new FileOutputStream(localFile)) {
      fos.write(expected.getBytes());
    }
    ConfigUtil.addLink(conf,
        "/internalDir/internalDir2/linkToLocalFile", localFile.toURI());

    try (FileSystem fs = FileSystem.get(FsConstants.VIEWFS_URI, conf)) {
      ContentSummary summaryAfter =
          fs.getContentSummary(new Path("/internalDir"));
      assertEquals("The file count didn't match",
          summaryBefore.getFileCount() + 1,
          summaryAfter.getFileCount());
      assertEquals("The directory count didn't match",
          summaryBefore.getLength() + expected.length(),
          summaryAfter.getLength());
    }
  }
