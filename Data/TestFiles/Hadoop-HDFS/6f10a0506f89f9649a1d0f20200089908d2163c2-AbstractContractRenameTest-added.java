        isSupported(RENAME_RETURNS_FALSE_IF_DEST_EXISTS);
    assertFalse(RENAME_OVERWRITES_DEST + " and " +
        RENAME_RETURNS_FALSE_IF_DEST_EXISTS + " cannot be both supported",
        renameOverwritesDest && renameReturnsFalseOnRenameDestExists);
    String expectedTo = "expected rename(" + srcFile + ", " + destFile + ") to ";

      // rename is rejected by returning 'false' or throwing an exception
      destUnchanged = !renamed;
        assertTrue(expectedTo + "overwrite destination, but got false",
            renamed);
      } else if (renameReturnsFalseOnRenameDestExists) {
        assertFalse(expectedTo + "be rejected with false, but destination " +
            "was overwritten", renamed);
      } else if (renamed) {
        String destDirLS = generateAndLogErrorListing(srcFile, destFile);
        getLogger().error("dest dir {}", destDirLS);

        fail(expectedTo + "be rejected with exception, but got overwritten");
        fail(expectedTo + "be rejected with exception, but got false");
      // rename(file, file2) should throw exception iff
      // it neither overwrites nor returns false
      assertFalse(expectedTo + "overwrite destination, but got exception",
          renameOverwritesDest);
      assertFalse(expectedTo + "be rejected with false, but got exception",
          renameReturnsFalseOnRenameDestExists);


