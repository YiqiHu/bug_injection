
import java.io.IOException;

import org.apache.hadoop.fs.LocalFileSystem;
  FileSystemTestHelper fileSystemTestHelper = new FileSystemTestHelper();

  class TestLFS extends LocalFileSystem {
    Path home;
    TestLFS() throws IOException {
      this(new Path(fileSystemTestHelper.getTestRootDir()));
    }
    TestLFS(Path home) throws IOException {
      super();
      this.home = home;
    }
    @Override
    public Path getHomeDirectory() {
      return home;
    }
  }
    fsTarget = FileSystem.getLocal(new Configuration());
    fsTarget.mkdirs(new Path(fileSystemTestHelper.
        getTestRootPath(fsTarget), "dir1"));
        fsTarget, new Path(fsTarget.getHomeDirectory(), ".Trash/Current"));
