import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
    OutputStream os = new FileOutputStream(simpleZip);
    ZipOutputStream tos = new ZipOutputStream(os);
    try {
      ZipEntry ze = new ZipEntry("foo");
      byte[] data = "some-content".getBytes("UTF-8");
      ze.setSize(data.length);
      tos.putNextEntry(ze);
      tos.write(data);
      tos.closeEntry();
    } finally {
      tos.close();

    Verify.exists(new File(tmp, "foo"));
    assertEquals(12, new File(tmp, "foo").length());
    try (ZipOutputStream tos = new ZipOutputStream(os)) {
      ZipEntry ze = new ZipEntry("../foo");
      tos.putNextEntry(ze);
      tos.closeEntry();
