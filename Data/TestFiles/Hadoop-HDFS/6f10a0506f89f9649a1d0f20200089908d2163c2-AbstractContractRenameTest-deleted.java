        !isSupported(RENAME_RETURNS_FALSE_IF_DEST_EXISTS);
      // the filesystem supports rename(file, file2) by overwriting file2

      assertTrue("Rename returned false", renamed);
      destUnchanged = false;
        // rename is rejected by returning 'false' or throwing an exception
        if (renamed && !renameReturnsFalseOnRenameDestExists) {
          //expected an exception
          String destDirLS = generateAndLogErrorListing(srcFile, destFile);
          getLogger().error("dest dir {}", destDirLS);
          fail("expected rename(" + srcFile + ", " + destFile + " ) to fail," +
               " but got success and destination of " + destDirLS);
        }
