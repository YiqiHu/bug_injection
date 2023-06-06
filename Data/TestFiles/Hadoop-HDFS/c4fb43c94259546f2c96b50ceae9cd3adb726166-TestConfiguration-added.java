
  @Test
  public void testCDATA() throws IOException {
    String xml = new String(
        "<configuration>" +
          "<property>" +
            "<name>cdata</name>" +
            "<value><![CDATA[>cdata]]></value>" +
          "</property>\n" +
          "<property>" +
            "<name>cdata-multiple</name>" +
            "<value><![CDATA[>cdata1]]> and <![CDATA[>cdata2]]></value>" +
          "</property>\n" +
          "<property>" +
            "<name>cdata-multiline</name>" +
            "<value><![CDATA[>cdata\nmultiline<>]]></value>" +
          "</property>\n" +
          "<property>" +
            "<name>cdata-whitespace</name>" +
            "<value>  prefix <![CDATA[>cdata]]>\nsuffix  </value>" +
          "</property>\n" +
        "</configuration>");
    Configuration conf = checkCDATA(xml.getBytes());
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    conf.writeXml(os);
    checkCDATA(os.toByteArray());
  }

  private static Configuration checkCDATA(byte[] bytes) {
    Configuration conf = new Configuration(false);
    conf.addResource(new ByteArrayInputStream(bytes));
    assertEquals(">cdata", conf.get("cdata"));
    assertEquals(">cdata1 and >cdata2", conf.get("cdata-multiple"));
    assertEquals(">cdata\nmultiline<>", conf.get("cdata-multiline"));
    assertEquals("  prefix >cdata\nsuffix  ", conf.get("cdata-whitespace"));
    return conf;
  }
