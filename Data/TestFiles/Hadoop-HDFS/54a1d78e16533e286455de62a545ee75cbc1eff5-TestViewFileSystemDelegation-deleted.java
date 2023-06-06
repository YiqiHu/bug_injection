  @Test
  public void testVerifyChecksum() throws Exception {
    checkVerifyChecksum(false);
    checkVerifyChecksum(true);
  }

  void checkVerifyChecksum(boolean flag) {
    viewFs.setVerifyChecksum(flag);
    assertEquals(flag, fs1.getVerifyChecksum());
    assertEquals(flag, fs2.getVerifyChecksum());
  }

