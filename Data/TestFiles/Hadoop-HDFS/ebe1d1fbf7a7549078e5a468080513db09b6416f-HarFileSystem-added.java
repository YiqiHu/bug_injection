import java.util.concurrent.ConcurrentHashMap;

    for (String child: parent.children) {
      Path p = new Path(parentString + child);
      statuses.add(toFileStatus(metadata.archive.get(p)));
  private FileStatus toFileStatus(HarStatus h) throws IOException {
    final Path p = h.isDir ? archivePath : new Path(archivePath, h.partName);
    FileStatus underlying = metadata.getPartFileStatus(p);
    return toFileStatus(hstatus);
      statuses.add(toFileStatus(hstatus));
    // keys are always the internal har path.
    private Map<Path, FileStatus> partFileStatuses = new ConcurrentHashMap<>();
    public FileStatus getPartFileStatus(Path path) throws IOException {
      Path partPath = getPathInHar(path);
        status = fs.getFileStatus(path);
    private void addPartFileStatuses(Path path) throws IOException {
      for (FileStatus stat : fs.listStatus(path)) {
        partFileStatuses.put(getPathInHar(stat.getPath()), stat);
      }
    }


        // pre-populate part cache.
        addPartFileStatuses(archiveIndexPath.getParent());
        LineReader aLin = null;
        long pos = -1;
          if (pos != s.begin) {
            pos = s.begin;
            aIn.seek(s.begin);
            aLin = new LineReader(aIn, getConf());
          }

          while (pos < s.end) {
            pos += aLin.readLine(line);
