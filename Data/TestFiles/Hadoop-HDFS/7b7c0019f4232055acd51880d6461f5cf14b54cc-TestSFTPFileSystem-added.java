
  @Test
  public void testCloseFileSystemClosesConnectionPool() throws Exception {
    SFTPFileSystem fs = (SFTPFileSystem) sftpFs;
    fs.getHomeDirectory();
    assertThat(fs.getConnectionPool().getLiveConnCount(), is(1));
    fs.close();
    assertThat(fs.getConnectionPool().getLiveConnCount(), is(0));
    ///making sure that re-entrant close calls are safe
    fs.close();
  }
