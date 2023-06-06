import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
    try (ZipInputStream zip = new ZipInputStream(inputStream)) {
      for(ZipEntry entry = zip.getNextEntry();
          entry = zip.getNextEntry()) {
    Enumeration<? extends ZipEntry> entries;
      entries = zipFile.entries();
        ZipEntry entry = entries.nextElement();
