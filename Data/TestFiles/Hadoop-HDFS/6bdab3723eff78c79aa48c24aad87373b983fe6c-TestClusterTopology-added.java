  @Test
  public void testNodeBaseNormalizeRemoveLeadingSlash() {
    assertEquals("/d1", NodeBase.normalize("/d1///"));
    assertEquals("/d1", NodeBase.normalize("/d1/"));
    assertEquals("/d1", NodeBase.normalize("/d1"));
    assertEquals("", NodeBase.normalize("///"));
    assertEquals("", NodeBase.normalize("/"));
  }

