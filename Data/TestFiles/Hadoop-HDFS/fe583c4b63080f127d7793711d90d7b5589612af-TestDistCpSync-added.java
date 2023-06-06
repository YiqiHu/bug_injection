import org.apache.hadoop.fs.contract.ContractTestUtils;
  @Test
  public void testRenameWithFilter() throws Exception {
    java.nio.file.Path filterFile = null;
    try {
      Path sourcePath = new Path(dfs.getWorkingDirectory(), "source");

      // Create some dir inside source
      dfs.mkdirs(new Path(sourcePath, "dir1"));
      dfs.mkdirs(new Path(sourcePath, "dir2"));

      // Allow & Create snapshot at source.
      dfs.allowSnapshot(sourcePath);
      dfs.createSnapshot(sourcePath, "s1");

      filterFile = Files.createTempFile("filters", "txt");
      String str = ".*filterDir1.*";
      try (BufferedWriter writer = new BufferedWriter(
          new FileWriter(filterFile.toString()))) {
        writer.write(str);
      }
      final DistCpOptions.Builder builder =
          new DistCpOptions.Builder(new ArrayList<>(Arrays.asList(sourcePath)),
              target).withFiltersFile(filterFile.toString())
              .withSyncFolder(true);
      new DistCp(conf, builder.build()).execute();

      // Check the two directories get copied.
      ContractTestUtils
          .assertPathExists(dfs, "dir1 should get copied to target",
              new Path(target, "dir1"));
      ContractTestUtils
          .assertPathExists(dfs, "dir2 should get copied to target",
              new Path(target, "dir2"));

      // Allow & create initial snapshots on target.
      dfs.allowSnapshot(target);
      dfs.createSnapshot(target, "s1");

      // Now do a rename to a filtered name on source.
      dfs.rename(new Path(sourcePath, "dir1"),
          new Path(sourcePath, "filterDir1"));

      ContractTestUtils
          .assertPathExists(dfs, "'filterDir1' should be there on source",
              new Path(sourcePath, "filterDir1"));

      // Create the incremental snapshot.
      dfs.createSnapshot(sourcePath, "s2");

      final DistCpOptions.Builder diffBuilder =
          new DistCpOptions.Builder(new ArrayList<>(Arrays.asList(sourcePath)),
              target).withUseDiff("s1", "s2")
              .withFiltersFile(filterFile.toString()).withSyncFolder(true);
      new DistCp(conf, diffBuilder.build()).execute();

      // Check the only qualified directory dir2 is there in target
      ContractTestUtils.assertPathExists(dfs, "dir2 should be there on target",
          new Path(target, "dir2"));

      // Check the filtered directory is not there.
      ContractTestUtils.assertPathDoesNotExist(dfs,
          "Filtered directory 'filterDir1' shouldn't get copied",
          new Path(target, "filterDir1"));

      // Check the renamed directory gets deleted.
      ContractTestUtils.assertPathDoesNotExist(dfs,
          "Renamed directory 'dir1' should get deleted",
          new Path(target, "dir1"));

      // Check the filtered directory isn't there in the home directory.
      ContractTestUtils.assertPathDoesNotExist(dfs,
          "Filtered directory 'filterDir1' shouldn't get copied to home directory",
          new Path("filterDir1"));
    } finally {
      deleteFilterFile(filterFile);
    }
  }

