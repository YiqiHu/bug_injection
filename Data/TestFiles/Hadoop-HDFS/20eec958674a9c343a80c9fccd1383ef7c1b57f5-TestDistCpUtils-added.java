import java.io.FileNotFoundException;
import static org.junit.Assert.assertNotEquals;
    assertStatusEqual(fs, dst, srcStatus);
  }

  private void assertStatusEqual(final FileSystem fs,
      final Path dst,
      final CopyListingFileStatus srcStatus) throws IOException {
    FileStatus destStatus = fs.getFileStatus(dst);
    CopyListingFileStatus dstStatus = new CopyListingFileStatus(
        destStatus);

    String text = String.format("Source %s; dest %s: wrong ", srcStatus,
        destStatus);
    assertEquals(text + "permission",
        srcStatus.getPermission(), dstStatus.getPermission());
    assertEquals(text + "owner",
        srcStatus.getOwner(), dstStatus.getOwner());
    assertEquals(text + "group",
        srcStatus.getGroup(), dstStatus.getGroup());
    assertEquals(text + "accessTime",
        srcStatus.getAccessTime(), dstStatus.getAccessTime());
    assertEquals(text + "modificationTime",
        srcStatus.getModificationTime(), dstStatus.getModificationTime());
    assertEquals(text + "replication",
        srcStatus.getReplication(), dstStatus.getReplication());
  }

  private void assertStatusNotEqual(final FileSystem fs,
      final Path dst,
      final CopyListingFileStatus srcStatus) throws IOException {
    FileStatus destStatus = fs.getFileStatus(dst);
    CopyListingFileStatus dstStatus = new CopyListingFileStatus(
        destStatus);

    String text = String.format("Source %s; dest %s: wrong ",
        srcStatus, destStatus);
    // FileStatus.equals only compares path field,
    // must explicitly compare all fields
    assertNotEquals(text + "permission",
        srcStatus.getPermission(), dstStatus.getPermission());
    assertNotEquals(text + "owner",
        srcStatus.getOwner(), dstStatus.getOwner());
    assertNotEquals(text + "group",
        srcStatus.getGroup(), dstStatus.getGroup());
    assertNotEquals(text + "accessTime",
        srcStatus.getAccessTime(), dstStatus.getAccessTime());
    assertNotEquals(text + "modificationTime",
        srcStatus.getModificationTime(), dstStatus.getModificationTime());
    assertNotEquals(text + "replication",
        srcStatus.getReplication(), dstStatus.getReplication());
  }


  @Test
  public void testSkipsNeedlessAttributes() throws Exception {
    FileSystem fs = FileSystem.get(config);

    // preserve replication, block size, user, group, permission,
    // checksum type and timestamps

    Path src = new Path("/tmp/testSkipsNeedlessAttributes/source");
    Path dst = new Path("/tmp/testSkipsNeedlessAttributes/dest");

    // there is no need to actually create a source file, just a file
    // status of one
    CopyListingFileStatus srcStatus = new CopyListingFileStatus(
        new FileStatus(0, false, 1, 32, 0, src));

    // if an attribute is needed, preserve will fail to find the file
    EnumSet<FileAttribute> attrs = EnumSet.of(FileAttribute.ACL,
        FileAttribute.GROUP,
        FileAttribute.PERMISSION,
        FileAttribute.TIMES,
        FileAttribute.XATTR);
    for (FileAttribute attr : attrs) {
      intercept(FileNotFoundException.class, () ->
          DistCpUtils.preserve(fs, dst, srcStatus,
              EnumSet.of(attr),
              false));
    }

    // but with the preservation flags only used
    // in file creation, this does not happen
    DistCpUtils.preserve(fs, dst, srcStatus,
        EnumSet.of(
            FileAttribute.BLOCKSIZE,
            FileAttribute.CHECKSUMTYPE),
        false);
    assertStatusEqual(fs, dest, srcStatus);

    assertStatusNotEqual(fs, dst, srcStatus);
    assertStatusEqual(fs, f2, srcStatus);
    assertStatusEqual(fs, f0, srcStatus);
