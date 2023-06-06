  private static Set<TimelineEntity> checkQuery(HBaseTimelineReaderImpl htr)
      throws IOException {
    return htr.getEntities(context, MONITOR_FILTERS, DATA_TO_RETRIEVE);
