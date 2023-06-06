import org.apache.hadoop.fs.azurebfs.services.AbfsClientRenameResult;
import static org.apache.hadoop.fs.azurebfs.AbfsStatistic.METADATA_INCOMPLETE_RENAME_FAILURES;
import static org.apache.hadoop.fs.azurebfs.AbfsStatistic.RENAME_RECOVERY;
        final AbfsClientRenameResult abfsClientRenameResult =
                continuation, tracingContext, sourceEtag, false);
        AbfsRestOperation op = abfsClientRenameResult.getOp();
        recovered |= abfsClientRenameResult.isRenameRecovered();
        populateRenameRecoveryStatistics(abfsClientRenameResult);
  public AbfsClient getClient() {

  /**
   * Increment rename recovery based counters in IOStatistics.
   *
   * @param abfsClientRenameResult Result of an ABFS rename operation.
   */
  private void populateRenameRecoveryStatistics(
      AbfsClientRenameResult abfsClientRenameResult) {
    if (abfsClientRenameResult.isRenameRecovered()) {
      abfsCounters.incrementCounter(RENAME_RECOVERY, 1);
    }
    if (abfsClientRenameResult.isIncompleteMetadataState()) {
      abfsCounters.incrementCounter(METADATA_INCOMPLETE_RENAME_FAILURES, 1);
    }
  }
