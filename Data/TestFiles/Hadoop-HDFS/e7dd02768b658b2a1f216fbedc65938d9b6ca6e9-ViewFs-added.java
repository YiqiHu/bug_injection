        try {
          String linkedPath = inodelink.getTargetFileSystem()
              .getUri().getPath();
          FileStatus status = ((ChRootedFs)inodelink.getTargetFileSystem())
              .getMyFs().getFileStatus(new Path(linkedPath));
          result = new FileStatus(status.getLen(), false,
            status.getReplication(), status.getBlockSize(),
            status.getModificationTime(), status.getAccessTime(),
            status.getPermission(), status.getOwner(), status.getGroup(),
            inodelink.getTargetLink(),
            new Path(inode.fullPath).makeQualified(
                myUri, null));
        } catch (FileNotFoundException ex) {
          result = new FileStatus(0, false, 0, 0, creationTime, creationTime,
        }
          try {
            String linkedPath = link.getTargetFileSystem().getUri().getPath();
            FileStatus status = ((ChRootedFs)link.getTargetFileSystem())
                .getMyFs().getFileStatus(new Path(linkedPath));
            result[i++] = new FileStatus(status.getLen(), false,
              status.getReplication(), status.getBlockSize(),
              status.getModificationTime(), status.getAccessTime(),
              status.getPermission(), status.getOwner(), status.getGroup(),
              link.getTargetLink(),
              new Path(inode.fullPath).makeQualified(
                  myUri, null));
          } catch (FileNotFoundException ex) {
            result[i++] = new FileStatus(0, false, 0, 0,
              creationTime, creationTime, PERMISSION_555,
              ugi.getShortUserName(), ugi.getPrimaryGroupName(),
              link.getTargetLink(),
              new Path(inode.fullPath).makeQualified(
                  myUri, null));
          }
