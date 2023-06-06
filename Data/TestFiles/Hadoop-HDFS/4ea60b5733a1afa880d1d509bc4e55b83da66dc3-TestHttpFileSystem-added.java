import org.junit.jupiter.api.BeforeEach;
import java.util.stream.IntStream;
  private final Configuration conf = new Configuration(false);

  @BeforeEach
  public void setUp() {
    conf.set("fs.http.impl", HttpFileSystem.class.getCanonicalName());
  }

      IntStream.rangeClosed(1, 3).forEach(i -> server.enqueue(new MockResponse().setBody(data)));
      assertSameData(fs, new Path(new URL(uri.toURL(), "/foo").toURI()), data);
      assertSameData(fs, new Path("/foo"), data);
      assertSameData(fs, new Path("foo"), data);

  @Test
  public void testHttpFileStatus() throws IOException, URISyntaxException, InterruptedException {
    URI uri = new URI("http://www.example.com");
    FileSystem fs = FileSystem.get(uri, conf);
    URI expectedUri = uri.resolve("/foo");
    assertEquals(fs.getFileStatus(new Path(new Path(uri), "/foo")).getPath().toUri(), expectedUri);
    assertEquals(fs.getFileStatus(new Path("/foo")).getPath().toUri(), expectedUri);
    assertEquals(fs.getFileStatus(new Path("foo")).getPath().toUri(), expectedUri);
  }

  private void assertSameData(FileSystem fs, Path path, String data) throws IOException {
    try (InputStream is = fs.open(
            path,
            4096)) {
      byte[] buf = new byte[data.length()];
      IOUtils.readFully(is, buf, 0, buf.length);
      assertEquals(data, new String(buf, StandardCharsets.UTF_8));
    }
  }
