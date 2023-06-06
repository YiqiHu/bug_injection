    final String[] targetDirLinkList;
        final T targetMergeFs, final String[] aTargetDirLinkList) {
        final String aTargetDirLink) throws URISyntaxException {
      targetDirLinkList = new String[1];
      targetDirLinkList[0] = new URI(aTargetDirLink).toString();
          targetFileSystem =
              fileSystemInitMethod.apply(URI.create(targetDirLinkList[0]));
          initAndGetTargetFs(), target);
      final String[] targetUris = StringUtils.getStrings(target);
          getTargetFileSystem(settings, StringUtils.stringToURI(targetUris)),
          targetUris);
          initAndGetTargetFs(), mergeSlashTarget);
              initAndGetTargetFs(), le.getTarget());
          initAndGetTargetFs(), theUri.toString());
