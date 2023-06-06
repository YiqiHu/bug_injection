import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
    try (OutputStream os = new FileOutputStream(simpleZip);
         ZipArchiveOutputStream tos = new ZipArchiveOutputStream(os)) {
      List<ZipArchiveEntry> ZipArchiveList = new ArrayList<>(7);
      int count = 0;
      // create 7 files to verify permissions
      for (int i = 0; i < 7; i++) {
        ZipArchiveList.add(new ZipArchiveEntry("foo_" + i));
        ZipArchiveEntry archiveEntry = ZipArchiveList.get(i);
        archiveEntry.setUnixMode(count += 0100);
        byte[] data = "some-content".getBytes("UTF-8");
        archiveEntry.setSize(data.length);
        tos.putArchiveEntry(archiveEntry);
        tos.write(data);
      }
      tos.closeArchiveEntry();

    File foo0 = new File(tmp, "foo_0");
    File foo1 = new File(tmp, "foo_1");
    File foo2 = new File(tmp, "foo_2");
    File foo3 = new File(tmp, "foo_3");
    File foo4 = new File(tmp, "foo_4");
    File foo5 = new File(tmp, "foo_5");
    File foo6 = new File(tmp, "foo_6");
    assertTrue(foo0.exists());
    assertTrue(foo1.exists());
    assertTrue(foo2.exists());
    assertTrue(foo3.exists());
    assertTrue(foo4.exists());
    assertTrue(foo5.exists());
    assertTrue(foo6.exists());
    assertEquals(12, foo0.length());
    // tests whether file foo_0 has executable permissions
    assertTrue("file lacks execute permissions", foo0.canExecute());
    assertFalse("file has write permissions", foo0.canWrite());
    assertFalse("file has read permissions", foo0.canRead());
    // tests whether file foo_1 has writable permissions
    assertFalse("file has execute permissions", foo1.canExecute());
    assertTrue("file lacks write permissions", foo1.canWrite());
    assertFalse("file has read permissions", foo1.canRead());
    // tests whether file foo_2 has executable and writable permissions
    assertTrue("file lacks execute permissions", foo2.canExecute());
    assertTrue("file lacks write permissions", foo2.canWrite());
    assertFalse("file has read permissions", foo2.canRead());
    // tests whether file foo_3 has readable permissions
    assertFalse("file has execute permissions", foo3.canExecute());
    assertFalse("file has write permissions", foo3.canWrite());
    assertTrue("file lacks read permissions", foo3.canRead());
    // tests whether file foo_4 has readable and executable permissions
    assertTrue("file lacks execute permissions", foo4.canExecute());
    assertFalse("file has write permissions", foo4.canWrite());
    assertTrue("file lacks read permissions", foo4.canRead());
    // tests whether file foo_5 has readable and writable permissions
    assertFalse("file has execute permissions", foo5.canExecute());
    assertTrue("file lacks write permissions", foo5.canWrite());
    assertTrue("file lacks read permissions", foo5.canRead());
    // tests whether file foo_6 has readable, writable and executable permissions
    assertTrue("file lacks execute permissions", foo6.canExecute());
    assertTrue("file lacks write permissions", foo6.canWrite());
    assertTrue("file lacks read permissions", foo6.canRead());
    try (ZipArchiveOutputStream tos = new ZipArchiveOutputStream(os)) {
      ZipArchiveEntry ze = new ZipArchiveEntry("../foo");
      tos.putArchiveEntry(ze);
      tos.closeArchiveEntry();
