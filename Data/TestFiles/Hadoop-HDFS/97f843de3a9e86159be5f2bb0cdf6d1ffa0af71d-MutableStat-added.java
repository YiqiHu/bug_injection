import org.apache.hadoop.util.Time;

  private long snapshotTimeStamp = 0;
  private boolean updateTimeStamp = false;
  /**
   * Set whether to update the snapshot time or not.
   * @param updateTimeStamp enable update stats snapshot timestamp
   */
  public synchronized void setUpdateTimeStamp(boolean updateTimeStamp) {
    this.updateTimeStamp = updateTimeStamp;
  }
   * Add a snapshot to the metric.
          if (updateTimeStamp) {
            snapshotTimeStamp = Time.monotonicNow();
          }
  /**
   * Return the SampleStat snapshot timestamp
   */
  public long getSnapshotTimeStamp() {
    return snapshotTimeStamp;
  }
