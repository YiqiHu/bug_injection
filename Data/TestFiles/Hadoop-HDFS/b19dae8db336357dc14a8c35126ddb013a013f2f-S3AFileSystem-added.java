        if (isCSEEnabled) {
          throw new PathIOException(uri.toString(), "S3-CSE cannot be used "
              + "with S3Guard");
        }
