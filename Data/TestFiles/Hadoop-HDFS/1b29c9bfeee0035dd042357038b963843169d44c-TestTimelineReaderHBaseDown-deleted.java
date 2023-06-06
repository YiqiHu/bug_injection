  private static void checkQuery(HBaseTimelineReaderImpl htr) throws
      IOException {
    Set<TimelineEntity> entities = htr.getEntities(context, MONITOR_FILTERS,
        DATA_TO_RETRIEVE);
