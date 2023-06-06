    //See HADOOP-17323.
    assertTrue("root path should always exist", fs.exists(root));
    assertTrue("getFileStatus on root should always return a directory",
            fs.getFileStatus(root).isDirectory());
