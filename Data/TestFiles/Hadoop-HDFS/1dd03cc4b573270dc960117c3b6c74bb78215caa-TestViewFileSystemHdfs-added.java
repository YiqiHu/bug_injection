import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.security.AccessControlException;

  @Test
  public void testTargetFileSystemLazyInitializationWithUgi() throws Exception {
    final Map<String, FileSystem> map = new HashMap<>();
    final Path user1Path = new Path("/data/user1");

    // Scenario - 1: Create FileSystem with the current user context
    // Both mkdir and delete should be successful
    FileSystem fs = FileSystem.get(FsConstants.VIEWFS_URI, conf);
    fs.mkdirs(user1Path);
    fs.delete(user1Path, false);

    // Scenario - 2: Create FileSystem with the a different user context
    final UserGroupInformation userUgi = UserGroupInformation
        .createUserForTesting("user1@HADOOP.COM", new String[]{"hadoop"});
    userUgi.doAs(new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws IOException {
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        String doAsUserName = ugi.getUserName();
        assertEquals(doAsUserName, "user1@HADOOP.COM");

        FileSystem viewFS = FileSystem.get(FsConstants.VIEWFS_URI, conf);
        map.put("user1", viewFS);
        return null;
      }
    });

    // Try creating a directory with the file context created by a different ugi
    // Though we are running the mkdir with the current user context, the
    // target filesystem will be instantiated by the ugi with which the
    // file context was created.
    try {
      FileSystem otherfs = map.get("user1");
      otherfs.mkdirs(user1Path);
      fail("This mkdir should fail");
    } catch (AccessControlException ex) {
      // Exception is expected as the FileSystem was created with ugi of user1
      // So when we are trying to access the /user/user1 path for the first
      // time, the corresponding file system is initialized and it tries to
      // execute the task with ugi with which the FileSystem was created.
    }

    // Change the permission of /data path so that user1 can create a directory
    fsTarget.setOwner(new Path(targetTestRoot, "data"),
        "user1", "test2");
    // set permission of target to allow rename to target
    fsTarget.setPermission(new Path(targetTestRoot, "data"),
        new FsPermission("775"));

    userUgi.doAs(new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws IOException {
        FileSystem viewFS = FileSystem.get(FsConstants.VIEWFS_URI, conf);
        map.put("user1", viewFS);
        return null;
      }
    });

    // Although we are running with current user context, and current user
    // context does not have write permission, we are able to create the
    // directory as its using ugi of user1 which has write permission.
    FileSystem otherfs = map.get("user1");
    otherfs.mkdirs(user1Path);
    String owner = otherfs.getFileStatus(user1Path).getOwner();
    assertEquals("The owner did not match ", owner, userUgi.getShortUserName());
    otherfs.delete(user1Path, false);
  }
