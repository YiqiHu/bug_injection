  private FileSystemTestHelper fileSystemTestHelper;
    Configuration targetFSConf = new Configuration();
    targetFSConf.setClass("fs.file.impl", TestTrash.TestLFS.class, FileSystem.class);

    fsTarget = FileSystem.getLocal(targetFSConf);
    fileSystemTestHelper = new FileSystemTestHelper(fsTarget.getHomeDirectory().toUri().getPath());


    /*
     * Need to set the fs.file.impl to TestViewFsTrash.TestLFS. Otherwise, it will load
     * LocalFileSystem implementation which uses System.getProperty("user.home") for homeDirectory.
     */
    conf.setClass("fs.file.impl", TestTrash.TestLFS.class, FileSystem.class);

        fsView, new Path(fileSystemTestHelper.getTestRootPath(fsView), ".Trash/Current"));
