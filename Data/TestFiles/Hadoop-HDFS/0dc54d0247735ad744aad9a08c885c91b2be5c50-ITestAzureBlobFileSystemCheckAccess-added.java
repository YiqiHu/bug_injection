import com.google.common.collect.Lists;

    String orgClientId = getConfiguration().get(FS_AZURE_BLOB_FS_CLIENT_ID);
    String orgClientSecret = getConfiguration()
        .get(FS_AZURE_BLOB_FS_CLIENT_SECRET);
    Boolean orgCreateFileSystemDurungInit = getConfiguration()
        .getBoolean(AZURE_CREATE_REMOTE_FILESYSTEM_DURING_INITIALIZATION, true);
    getRawConfiguration().set(FS_AZURE_BLOB_FS_CLIENT_ID,
        getConfiguration().get(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_ID));
    getRawConfiguration().set(FS_AZURE_BLOB_FS_CLIENT_SECRET, getConfiguration()
        .get(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_SECRET));
    FileSystem fs = FileSystem.newInstance(getRawConfiguration());
    getRawConfiguration().set(FS_AZURE_BLOB_FS_CLIENT_ID, orgClientId);
    getRawConfiguration().set(FS_AZURE_BLOB_FS_CLIENT_SECRET, orgClientSecret);
    getRawConfiguration()
        .setBoolean(AZURE_CREATE_REMOTE_FILESYSTEM_DURING_INITIALIZATION,
            orgCreateFileSystemDurungInit);
    this.testUserFs = fs;
    assumeHNSAndCheckAccessEnabled();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
            isCheckAccessEnabled);
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
    assumeHNSAndCheckAccessEnabled();
    setTestUserFs();
  private void assumeHNSAndCheckAccessEnabled() {
    Assume.assumeNotNull(getRawConfiguration().get(FS_AZURE_BLOB_FS_CLIENT_ID));
