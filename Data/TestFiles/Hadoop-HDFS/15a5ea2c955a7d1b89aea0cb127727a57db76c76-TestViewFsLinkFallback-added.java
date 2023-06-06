import java.util.List;
import org.apache.hadoop.security.token.Token;
  /**
   * Test getDelegationToken when fallback is configured.
   */
  @Test
  public void testGetDelegationToken() throws IOException {
    Configuration conf = new Configuration();
    conf.setBoolean(Constants.CONFIG_VIEWFS_MOUNT_LINKS_AS_SYMLINKS, false);
    ConfigUtil.addLink(conf, "/user",
        new Path(targetTestRoot.toString(), "user").toUri());
    ConfigUtil.addLink(conf, "/data",
        new Path(targetTestRoot.toString(), "data").toUri());
    ConfigUtil.addLinkFallback(conf, targetTestRoot.toUri());

    FileContext fcView =
        FileContext.getFileContext(FsConstants.VIEWFS_URI, conf);
    List<Token<?>> tokens = fcView.getDelegationTokens(new Path("/"), "tester");
    // Two tokens from the two mount points and one token from fallback
    assertEquals(3, tokens.size());
  }

