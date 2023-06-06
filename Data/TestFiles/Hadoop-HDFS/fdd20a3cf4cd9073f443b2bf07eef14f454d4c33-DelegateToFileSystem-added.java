import java.util.Optional;
      Optional<Path> parentPath = f.getOptionalParentPath();
      if (!parentPath.isPresent()) {
        throw new FileNotFoundException("Missing parent:" + f);
      }
      final FileStatus stat = getFileStatus(parentPath.get());
