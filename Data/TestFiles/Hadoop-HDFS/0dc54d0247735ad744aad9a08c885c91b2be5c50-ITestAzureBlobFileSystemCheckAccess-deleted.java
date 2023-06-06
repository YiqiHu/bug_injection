import java.lang.reflect.Field;
import com.google.common.collect.Lists;
import org.mockito.Mockito;
import org.apache.hadoop.fs.azurebfs.services.AuthType;
import static org.apache.hadoop.fs.azurebfs.constants.ConfigurationKeys.FS_AZURE_ACCOUNT_AUTH_TYPE_PROPERTY_NAME;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;
 *
 * Some of the tests in this class requires the following 3 configs set in the
 * test config file.
 * fs.azure.account.test.oauth2.client.id
 * fs.azure.account.test.oauth2.client.secret
 * fs.azure.check.access.testuser.guid
 * Set the above client id, secret and guid of a service principal which has no
 * RBAC on the account.
 *
    setTestUserFs();
    final String testClientIdConfKey =
        FS_AZURE_BLOB_FS_CLIENT_ID + "." + getAccountName();
    final String testClientId = getConfiguration()
        .getString(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_ID, "");
    getRawConfiguration().set(testClientIdConfKey, testClientId);
    final String clientSecretConfKey =
        FS_AZURE_BLOB_FS_CLIENT_SECRET + "." + getAccountName();
    final String testClientSecret = getConfiguration()
        .getString(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_SECRET, "");
    getRawConfiguration().set(clientSecretConfKey, testClientSecret);
    getRawConfiguration().set(FS_AZURE_ACCOUNT_AUTH_TYPE_PROPERTY_NAME,
        AuthType.OAuth.name());
    this.testUserFs = FileSystem.newInstance(getRawConfiguration());
    Assume.assumeTrue(FS_AZURE_TEST_NAMESPACE_ENABLED_ACCOUNT + " is false",
        isHNSEnabled);
    checkPrerequisites();
        isCheckAccessEnabled);
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_ID);
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_SECRET);
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_USER_GUID);

    //  When the driver does not know if the account is HNS enabled or not it
    //  makes a server call and fails
    intercept(AccessControlException.class,
        "\"This request is not authorized to perform this operation using "
            + "this permission.\", 403",
        () -> testUserFs.access(new Path("/"), FsAction.READ));

    //  When the driver has already determined if the account is HNS enabled
    //  or not, and as the account is non HNS the AzureBlobFileSystem#access
    //  acts as noop
    AzureBlobFileSystemStore mockAbfsStore =
        Mockito.mock(AzureBlobFileSystemStore.class);
    Mockito.when(mockAbfsStore.getIsNamespaceEnabled()).thenReturn(true);
    Field abfsStoreField = AzureBlobFileSystem.class.getDeclaredField(
        "abfsStore");
    abfsStoreField.setAccessible(true);
    abfsStoreField.set(testUserFs, mockAbfsStore);

    superUserFs.access(new Path("/"), FsAction.READ);
    checkPrerequisites();
    checkPrerequisites();
    checkPrerequisites();
    checkPrerequisites();
    checkPrerequisites();
    checkPrerequisites();
    checkPrerequisites();
  private void checkPrerequisites() {
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_ID);
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_CLIENT_SECRET);
    checkIfConfigIsSet(FS_AZURE_BLOB_FS_CHECKACCESS_TEST_USER_GUID);
  }
  private void checkIfConfigIsSet(String configKey){
    AbfsConfiguration conf = getConfiguration();
    Assume.assumeNotNull(configKey + " config missing", conf.get(configKey));
