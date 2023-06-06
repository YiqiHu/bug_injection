    URLConnection conn = path.toUri().toURL().openConnection();
    return new FileStatus(-1, false, 1, DEFAULT_BLOCK_SIZE, 0, path);
