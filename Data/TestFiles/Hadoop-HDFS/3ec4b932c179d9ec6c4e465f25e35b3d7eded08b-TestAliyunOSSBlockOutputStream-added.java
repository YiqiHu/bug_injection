import org.apache.hadoop.test.GenericTestUtils;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

  @Test
  public void testDirectoryAllocator() throws Throwable {
    Configuration conf = fs.getConf();
    File tmp = AliyunOSSUtils.createTmpFileForWrite("out-", 1024, conf);
    assertTrue("not found: " + tmp, tmp.exists());
    tmp.delete();

    // tmp should not in DeleteOnExitHook
    try {
      Class<?> c = Class.forName("java.io.DeleteOnExitHook");
      Field field = c.getDeclaredField("files");
      field.setAccessible(true);
      String name = field.getName();
      LinkedHashSet<String> files = (LinkedHashSet<String>)field.get(name);
      assertTrue("in DeleteOnExitHook", files.isEmpty());
      assertFalse("in DeleteOnExitHook",
          (new ArrayList<>(files)).contains(tmp.getPath()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDirectoryAllocatorRR() throws Throwable {
    File dir1 = GenericTestUtils.getRandomizedTestDir();
    File dir2 = GenericTestUtils.getRandomizedTestDir();
    dir1.mkdirs();
    dir2.mkdirs();

    Configuration conf = new Configuration();
    conf.set(BUFFER_DIR_KEY, dir1 + ", " + dir2);
    fs = AliyunOSSTestUtils.createTestFileSystem(conf);
    File tmp1 = AliyunOSSUtils.createTmpFileForWrite("out-", 1024, conf);
    tmp1.delete();
    File tmp2 = AliyunOSSUtils.createTmpFileForWrite("out-", 1024, conf);
    tmp2.delete();
    assertNotEquals("round robin not working",
        tmp1.getParent(), tmp2.getParent());
  }
