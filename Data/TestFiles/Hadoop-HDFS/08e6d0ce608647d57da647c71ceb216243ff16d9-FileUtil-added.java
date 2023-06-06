import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
    try (ZipArchiveInputStream zip = new ZipArchiveInputStream(inputStream)) {
      for(ZipArchiveEntry entry = zip.getNextZipEntry();
          entry = zip.getNextZipEntry()) {
          if (entry.getPlatform() == ZipArchiveEntry.PLATFORM_UNIX) {
            Files.setPosixFilePermissions(file.toPath(), permissionsFromMode(entry.getUnixMode()));
          }
  /**
   * The permission operation of this method only involves users, user groups, and others.
   * If SUID is set, only executable permissions are reserved.
   * @param mode Permissions are represented by numerical values
   * @return The original permissions for files are stored in collections
   */
  private static Set<PosixFilePermission> permissionsFromMode(int mode) {
    EnumSet<PosixFilePermission> permissions =
        EnumSet.noneOf(PosixFilePermission.class);
    addPermissions(permissions, mode, PosixFilePermission.OTHERS_READ,
        PosixFilePermission.OTHERS_WRITE, PosixFilePermission.OTHERS_EXECUTE);
    addPermissions(permissions, mode >> 3, PosixFilePermission.GROUP_READ,
        PosixFilePermission.GROUP_WRITE, PosixFilePermission.GROUP_EXECUTE);
    addPermissions(permissions, mode >> 6, PosixFilePermission.OWNER_READ,
        PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE);
    return permissions;
  }

  /**
   * Assign the original permissions to the file
   * @param permissions The original permissions for files are stored in collections
   * @param mode Use a value of type int to indicate permissions
   * @param r Read permission
   * @param w Write permission
   * @param x Execute permission
   */
  private static void addPermissions(
      Set<PosixFilePermission> permissions,
      int mode,
      PosixFilePermission r,
      PosixFilePermission w,
      PosixFilePermission x) {
    if ((mode & 1L) == 1L) {
      permissions.add(x);
    }
    if ((mode & 2L) == 2L) {
      permissions.add(w);
    }
    if ((mode & 4L) == 4L) {
      permissions.add(r);
    }
  }

    Enumeration<? extends ZipArchiveEntry> entries;
      entries = zipFile.getEntries();
        ZipArchiveEntry entry = entries.nextElement();
            if (entry.getPlatform() == ZipArchiveEntry.PLATFORM_UNIX) {
              Files.setPosixFilePermissions(file.toPath(), permissionsFromMode(entry.getUnixMode()));
            }
