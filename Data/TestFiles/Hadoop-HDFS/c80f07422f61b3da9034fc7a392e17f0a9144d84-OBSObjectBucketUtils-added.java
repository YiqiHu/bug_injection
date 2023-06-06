import java.util.Optional;
      Optional<Path> parent = p.getOptionalParentPath();
      if (!parent.isPresent()) {
        break;
      }
      p = parent.get();
      if (p.compareTo(sourcePath) == 0) {
