          shellRun("-touch", "-c", newFileName), is(0));
          "Expected successful touch with a specified modification time",

    // Verify -c option when file exists.
    String strTime = formatTimestamp(System.currentTimeMillis());
    Date dateObj = parseTimestamp(strTime);
    assertThat(
        "Expected successful touch on a non-existent file with -c option",
        shellRun("-touch", "-c", "-t", strTime, newFileName), is(0));
    FileStatus fileStatus = lfs.getFileStatus(newFile);
    assertThat(fileStatus.getAccessTime(), is(dateObj.getTime()));
    assertThat(fileStatus.getModificationTime(), is(dateObj.getTime()));
