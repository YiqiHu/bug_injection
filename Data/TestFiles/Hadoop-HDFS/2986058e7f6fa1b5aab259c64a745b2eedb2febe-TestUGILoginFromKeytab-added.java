import org.apache.hadoop.util.Time;
  /**
   * Login from keytab using the MiniKDC.
   */
  @Test
  public void testUGILoginFromKeytab() throws Exception {
    long beforeLogin = Time.now();
    String principal = "foo";
    File keytab = new File(workDir, "foo.keytab");
    kdc.createPrincipal(keytab, principal);

    UserGroupInformation.loginUserFromKeytab(principal, keytab.getPath());
    UserGroupInformation ugi = UserGroupInformation.getLoginUser();
    Assert.assertTrue("UGI should be configured to login from keytab",
        ugi.isFromKeytab());

    User user = getUser(ugi.getSubject());
    Assert.assertNotNull(user.getLogin());

    Assert.assertTrue("User login time is less than before login time, "
        + "beforeLoginTime:" + beforeLogin + " userLoginTime:" + user.getLastLogin(),
            user.getLastLogin() > beforeLogin);
  }

  public void testUGIReLoginFromKeytab() throws Exception {
    // Sleep for 2 secs to have a difference between first and second login
    Thread.sleep(2000);

