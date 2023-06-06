import java.io.DataOutputStream;
import java.io.IOException;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Trash;
import org.apache.hadoop.fs.TrashPolicyDefault;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.*;
import static org.apache.hadoop.fs.viewfs.Constants.*;
import static org.junit.Assert.*;

  @Test
  public void testLocalizedTrashInMoveToAppropriateTrash() throws IOException {
    Configuration conf2 = new Configuration(conf);
    Path testFile = new Path("/data/testfile.txt");

    // Enable moveToTrash and add a mount point for /data
    conf2.setLong(FS_TRASH_INTERVAL_KEY, 1);
    ConfigUtil.addLink(conf2, "/data", new Path(fileSystemTestHelper.getAbsoluteTestRootPath(fsTarget), "data").toUri());

    // Default case. file should be moved to fsTarget.getTrashRoot()/resolvedPath
    conf2.setBoolean(CONFIG_VIEWFS_TRASH_FORCE_INSIDE_MOUNT_POINT, false);
    try (FileSystem fsView2 = FileSystem.get(conf2)) {
      FileSystemTestHelper.createFile(fsView2, testFile);
      Path resolvedFile = fsView2.resolvePath(testFile);

      Trash.moveToAppropriateTrash(fsView2, testFile, conf2);
      Trash trash = new Trash(fsTarget, conf2);
      Path movedPath = Path.mergePaths(trash.getCurrentTrashDir(testFile), resolvedFile);
      ContractTestUtils.assertPathExists(fsTarget, "File not in trash", movedPath);
    }

    // Turn on localized trash. File should be moved to viewfs:/data/.Trash/{user}/Current.
    conf2.setBoolean(CONFIG_VIEWFS_TRASH_FORCE_INSIDE_MOUNT_POINT, true);
    try (FileSystem fsView2 = FileSystem.get(conf2)) {
      FileSystemTestHelper.createFile(fsView2, testFile);

      Trash.moveToAppropriateTrash(fsView2, testFile, conf2);
      Trash trash = new Trash(fsView2, conf2);
      Path movedPath = Path.mergePaths(trash.getCurrentTrashDir(testFile), testFile);
      ContractTestUtils.assertPathExists(fsView2, "File not in localized trash", movedPath);
    }
  }
