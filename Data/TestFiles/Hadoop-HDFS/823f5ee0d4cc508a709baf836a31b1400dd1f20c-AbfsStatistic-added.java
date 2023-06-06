      AbfsHttpConstants.HTTP_METHOD_POST),

  // Rename recovery
  RENAME_RECOVERY("rename_recovery",
      "Number of times Rename recoveries happened"),
  METADATA_INCOMPLETE_RENAME_FAILURES("metadata_incomplete_rename_failures",
      "Number of times rename operation failed due to metadata being "
          + "incomplete"),
  RENAME_PATH_ATTEMPTS("rename_path_attempts",
      "Number of times we attempt to rename a path internally");
