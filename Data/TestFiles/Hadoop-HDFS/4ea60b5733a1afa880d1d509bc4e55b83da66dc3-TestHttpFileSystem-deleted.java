    Configuration conf = new Configuration(false);
    conf.set("fs.http.impl", HttpFileSystem.class.getCanonicalName());

      server.enqueue(new MockResponse().setBody(data));
      try (InputStream is = fs.open(
          new Path(new URL(uri.toURL(), "/foo").toURI()),
          4096)) {
        byte[] buf = new byte[data.length()];
        IOUtils.readFully(is, buf, 0, buf.length);
        assertEquals(data, new String(buf, StandardCharsets.UTF_8));
      }
