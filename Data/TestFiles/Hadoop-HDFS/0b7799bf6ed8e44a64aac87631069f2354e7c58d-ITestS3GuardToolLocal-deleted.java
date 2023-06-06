import java.io.FileNotFoundException;
import org.junit.Assume;
    MetadataStore ms = getMetadataStore();
    Assume.assumeTrue("Test only applies when a local store is used for S3Guard;"
            + "Store is " + (ms == null ? "none" : ms.toString()),
        ms instanceof LocalMetadataStore);
    assertTrue(children.isAuthoritative());
    intercept(FileNotFoundException.class,
  @Test
  public void testInitNegativeRead() throws Throwable {
    runToFailure(INVALID_ARGUMENT,
        Init.NAME, "-meta", LOCAL_METADATA, "-region",
        "eu-west-1",
        READ_FLAG, "-10");
  }

