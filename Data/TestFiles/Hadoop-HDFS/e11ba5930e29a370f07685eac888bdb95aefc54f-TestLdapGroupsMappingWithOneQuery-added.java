import java.util.ArrayList;
import java.util.Set;
import javax.naming.directory.DirContext;
import org.mockito.stubbing.Stubber;
  public void setupMocks(List<String> listOfDNs) throws NamingException {
    buildListOfGroupDNs(listOfDNs).when(groupNames).next();
    when(groupNames.hasMore()).
      thenReturn(true).thenReturn(true).
      thenReturn(true).thenReturn(false);
  /**
   * Build and return a list of individually added group DNs such
   * that calls to .next() will result in a single value each time.
   *
   * @param listOfDNs
   * @return the stubber to use for the .when().next() call
   */
  private Stubber buildListOfGroupDNs(List<String> listOfDNs) {
    Stubber stubber = null;
    for (String s : listOfDNs) {
      if (stubber != null) {
        stubber.doReturn(s);
      } else {
        stubber = doReturn(s);
      }
    }
    return stubber;
  }


    // test fallback triggered by NamingException
    doTestGetGroupsWithFallback();
    List<String> groupDns = new ArrayList<>();
    groupDns.add("CN=abc,DC=foo,DC=bar,DC=com");
    groupDns.add("CN=xyz,DC=foo,DC=bar,DC=com");
    groupDns.add("CN=sss,DC=foo,DC=bar,DC=com");

    setupMocks(groupDns);
    TestLdapGroupsMapping groupsMapping = new TestLdapGroupsMapping();
    Assert.assertFalse("Second LDAP query should NOT have been called.",
            groupsMapping.isSecondaryQueryCalled());

  private void doTestGetGroupsWithFallback()
          throws NamingException {
    List<String> groupDns = new ArrayList<>();
    groupDns.add("CN=abc,DC=foo,DC=bar,DC=com");
    groupDns.add("CN=xyz,DC=foo,DC=bar,DC=com");
    groupDns.add("ipaUniqueID=e4a9a634-bb24-11ec-aec1-06ede52b5fe1," +
            "CN=sudo,DC=foo,DC=bar,DC=com");
    setupMocks(groupDns);
    String ldapUrl = "ldap://test";
    Configuration conf = getBaseConf(ldapUrl);
    // enable single-query lookup
    conf.set(LdapGroupsMapping.MEMBEROF_ATTR_KEY, "memberOf");
    conf.set(LdapGroupsMapping.LDAP_NUM_ATTEMPTS_KEY, "1");

    TestLdapGroupsMapping groupsMapping = new TestLdapGroupsMapping();
    groupsMapping.setConf(conf);
    // Username is arbitrary, since the spy is mocked to respond the same,
    // regardless of input
    List<String> groups = groupsMapping.getGroups("some_user");

    // expected to be empty due to invalid memberOf
    Assert.assertEquals(0, groups.size());

    // expect secondary query to be called: getGroups()
    Assert.assertTrue("Second LDAP query should have been called.",
            groupsMapping.isSecondaryQueryCalled());

    // We should have fallen back to the second query because first threw
    // NamingException expected count is 3 since testGetGroups calls
    // doTestGetGroups and doTestGetGroupsWithFallback in succession and
    // the count is across both test scenarios.
    verify(getContext(), times(3)).search(anyString(), anyString(),
            any(Object[].class), any(SearchControls.class));
  }

  private static final class TestLdapGroupsMapping extends LdapGroupsMapping {
    private boolean secondaryQueryCalled = false;
    public boolean isSecondaryQueryCalled() {
      return secondaryQueryCalled;
    }
    Set<String> lookupGroup(SearchResult result, DirContext c,
                                    int goUpHierarchy) throws NamingException {
      secondaryQueryCalled = true;
      return super.lookupGroup(result, c, goUpHierarchy);
    }
  }
}
