import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.fs.FsConstants;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.security.AccessControlException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

  @Test
  public void testTargetFileSystemLazyInitialization() throws Exception {
    final Map<String, FileContext> map = new HashMap<>();
    final Path user1Path = new Path("/data/user1");

    // Scenario - 1: Create FileContext with the current user context
    // Both mkdir and delete should be successful
    FileContext fs = FileContext.getFileContext(FsConstants.VIEWFS_URI, conf);
    fs.mkdir(user1Path, FileContext.DEFAULT_PERM, false);
    fs.delete(user1Path, false);

    // Scenario - 2: Create FileContext with the a different user context
    final UserGroupInformation userUgi = UserGroupInformation
        .createUserForTesting("user1@HADOOP.COM", new String[]{"hadoop"});
    userUgi.doAs(new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws IOException {
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        String doAsUserName = ugi.getUserName();
        assertEquals(doAsUserName, "user1@HADOOP.COM");

        FileContext viewFS = FileContext.getFileContext(
            FsConstants.VIEWFS_URI, conf);
        map.put("user1", viewFS);
        return null;
      }
    });

    // Try creating a directory with the file context created by a different ugi
    // Though we are running the mkdir with the current user context, the
    // target filesystem will be instantiated by the ugi with which the
    // file context was created.
    try {
      FileContext otherfs = map.get("user1");
      otherfs.mkdir(user1Path, FileContext.DEFAULT_PERM, false);
      fail("This mkdir should fail");
    } catch (AccessControlException ex) {
      // Exception is expected as the FileContext was created with ugi of user1
      // So when we are trying to access the /user/user1 path for the first
      // time, the corresponding file system is initialized and it tries to
      // execute the task with ugi with which the FileContext was created.
    }

    // Change the permission of /data path so that user1 can create a directory
    fcView.setOwner(new Path("/data"), "user1", "test2");
    // set permission of target to allow rename to target
    fcView.setPermission(new Path("/data"), new FsPermission("775"));

    userUgi.doAs(new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws IOException {
        FileContext viewFS = FileContext.getFileContext(
            FsConstants.VIEWFS_URI, conf);
        map.put("user1", viewFS);
        return null;
      }
    });

    // Although we are running with current user context, and current user
    // context does not have write permission, we are able to create the
    // directory as its using ugi of user1 which has write permission.
    FileContext otherfs = map.get("user1");
    otherfs.mkdir(user1Path, FileContext.DEFAULT_PERM, false);
    String owner = otherfs.getFileStatus(user1Path).getOwner();
    assertEquals("The owner did not match ", owner, userUgi.getShortUserName());
    otherfs.delete(user1Path, false);
  }
