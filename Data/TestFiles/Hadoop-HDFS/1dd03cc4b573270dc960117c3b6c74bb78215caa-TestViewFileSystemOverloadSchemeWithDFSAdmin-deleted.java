        new Path("hdfs://nonExistent" + HDFS_USER_FOLDER);
    int ret = ToolRunner.run(dfsAdmin, new String[] {"-safemode", "enter" });
