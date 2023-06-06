import org.junit.Before;
  @Before
  public void setupMocks() throws NamingException {
    String groupName1 = "CN=abc,DC=foo,DC=bar,DC=com";
    String groupName2 = "CN=xyz,DC=foo,DC=bar,DC=com";
    String groupName3 = "CN=sss,CN=foo,DC=bar,DC=com";
    doReturn(groupName1).doReturn(groupName2).doReturn(groupName3).
        when(groupNames).next();
    when(groupNames.hasMore()).thenReturn(true).thenReturn(true).
        thenReturn(true).thenReturn(false);
    LdapGroupsMapping groupsMapping = getGroupsMapping();
}
