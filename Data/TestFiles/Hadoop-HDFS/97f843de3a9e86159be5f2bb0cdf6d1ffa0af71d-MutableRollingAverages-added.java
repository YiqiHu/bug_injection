import org.apache.hadoop.util.Time;
  /**
   * This class maintains sub-sum and sub-total of SampleStat.
   */
    private final long snapshotTimeStamp;

    /**
     * Constructor for {@link SumAndCount}.
     *
     * @param sum sub-sum in sliding windows
     * @param count sub-total in sliding windows
     * @param snapshotTimeStamp when is a new SampleStat snapshot.
     */
    SumAndCount(final double sum, final long count,
        final long snapshotTimeStamp) {
      this.snapshotTimeStamp = snapshotTimeStamp;

    public long getSnapshotTimeStamp() {
      return snapshotTimeStamp;
    }
  /**
   * Time duration after which a record is considered stale.
   * {@link MutableRollingAverages} should be time-sensitive, and it should use
   * the time window length(i.e. NUM_WINDOWS_DEFAULT * WINDOW_SIZE_MS_DEFAULT)
   * as the valid time to make sure some too old record won't be use to compute
   * average.
   */
  private long recordValidityMs =
      NUM_WINDOWS_DEFAULT * WINDOW_SIZE_MS_DEFAULT;

          rate.lastStat().numSamples(),
          rate.getSnapshotTimeStamp());
        if (Time.monotonicNow() - sumAndCount.getSnapshotTimeStamp()
            < recordValidityMs) {
          totalCount += sumAndCount.getCount();
          totalSum += sumAndCount.getSum();
        }

  /**
   * Use for test only.
   */
  @VisibleForTesting
  public synchronized void setRecordValidityMs(long value) {
    this.recordValidityMs = value;
  }
