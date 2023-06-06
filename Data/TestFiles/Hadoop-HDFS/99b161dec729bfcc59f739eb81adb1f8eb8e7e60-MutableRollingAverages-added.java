  public synchronized void snapshot(MetricsRecordBuilder builder, boolean all) {
          if (Time.monotonicNow() - sumAndCount.getSnapshotTimeStamp()
              < recordValidityMs) {
            totalCount += sumAndCount.getCount();
            totalSum += sumAndCount.getSum();
          }
