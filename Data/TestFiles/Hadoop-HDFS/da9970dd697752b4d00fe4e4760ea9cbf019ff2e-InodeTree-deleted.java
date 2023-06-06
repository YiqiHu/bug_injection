    final URI[] targetDirLinkList;
        final T targetMergeFs, final URI[] aTargetDirLinkList) {
        final URI aTargetDirLink) {
      targetDirLinkList = new URI[1];
      targetDirLinkList[0] = aTargetDirLink;
          targetFileSystem = fileSystemInitMethod.apply(targetDirLinkList[0]);
          initAndGetTargetFs(), new URI(target));
      final URI[] targetUris = StringUtils.stringToURI(
          StringUtils.getStrings(target));
            getTargetFileSystem(settings, targetUris), targetUris);
          initAndGetTargetFs(),
          new URI(mergeSlashTarget));
              initAndGetTargetFs(), new URI(le.getTarget()));
          initAndGetTargetFs(), theUri);
