    String wrongFsUri = "hdfs://nonExistent";
        new Path(wrongFsUri + HDFS_USER_FOLDER);
    int ret = ToolRunner.run(dfsAdmin,
        new String[] {"-fs", wrongFsUri, "-safemode", "enter" });
