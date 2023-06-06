        result = new FileStatus(0, false, 0, 0, creationTime, creationTime,
          result[i++] = new FileStatus(0, false, 0, 0,
            creationTime, creationTime,
            PERMISSION_555, ugi.getShortUserName(), ugi.getPrimaryGroupName(),
            link.getTargetLink(),
            new Path(inode.fullPath).makeQualified(
                myUri, null));
