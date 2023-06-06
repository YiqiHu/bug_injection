      do {
        if (CollectionUtils.isNotEmpty(listing.getObjectSummaries()) ||
            CollectionUtils.isNotEmpty(listing.getCommonPrefixes())) {
          return new OSSFileStatus(0, true, 1, 0, 0, qualifiedPath, username);
        } else if (listing.isTruncated()) {
          listing = store.listObjects(key, 1000, listing.getNextMarker(),
              false);
        } else {
          throw new FileNotFoundException(
              path + ": No such file or directory!");
        }
      } while (true);
