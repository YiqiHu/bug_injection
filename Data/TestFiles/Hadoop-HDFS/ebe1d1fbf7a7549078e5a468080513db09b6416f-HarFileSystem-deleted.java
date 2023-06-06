    Path harPath = new Path(parentString);
    int harlen = harPath.depth();
    final Map<String, FileStatus> cache = new TreeMap<String, FileStatus>();

    for (HarStatus hstatus : metadata.archive.values()) {
      String child = hstatus.getName();
      if ((child.startsWith(parentString))) {
        Path thisPath = new Path(child);
        if (thisPath.depth() == harlen + 1) {
          statuses.add(toFileStatus(hstatus, cache));
        }
      }
   * @param cache caching the underlying file statuses
  private FileStatus toFileStatus(HarStatus h,
      Map<String, FileStatus> cache) throws IOException {
    FileStatus underlying = null;
    if (cache != null) {
      underlying = cache.get(h.partName);
    }
    if (underlying == null) {
      final Path p = h.isDir? archivePath: new Path(archivePath, h.partName);
      underlying = fs.getFileStatus(p);
      if (cache != null) {
        cache.put(h.partName, underlying);
      }
    }
    return toFileStatus(hstatus, null);
      statuses.add(toFileStatus(hstatus, null));
    private Map<Path, FileStatus> partFileStatuses = new HashMap<Path, FileStatus>();
    public FileStatus getPartFileStatus(Path partPath) throws IOException {
        status = fs.getFileStatus(partPath);
        LineReader aLin;
          read = 0;
          aIn.seek(s.begin);
          aLin = new LineReader(aIn, getConf());
          while (read + s.begin < s.end) {
            int tmp = aLin.readLine(line);
            read += tmp;
