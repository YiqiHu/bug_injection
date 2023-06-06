import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.test.LambdaTestUtils;

  @Test
  public void testListStatusWithNoGroups() throws Exception {
    final UserGroupInformation userUgi = UserGroupInformation
        .createUserForTesting("user@HADOOP.COM", new String[] {});
    userUgi.doAs(new PrivilegedExceptionAction<Object>() {
      @Override
      public Object run() throws Exception {
        String clusterName = Constants.CONFIG_VIEWFS_DEFAULT_MOUNT_TABLE;
        URI viewFsUri =
            new URI(FsConstants.VIEWFS_SCHEME, clusterName, "/", null, null);
        FileSystem vfs = FileSystem.get(viewFsUri, conf);
        LambdaTestUtils.intercept(IOException.class,
            "There is no primary group for UGI", () -> vfs
                .listStatus(new Path(viewFsUri.toString() + "internalDir")));
        return null;
      }
    });
  }

