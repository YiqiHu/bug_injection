          try {
            String linkedPath = link.getTargetFileSystem().getUri().getPath();
            FileStatus status =
                ((ChRootedFileSystem)link.getTargetFileSystem())
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
