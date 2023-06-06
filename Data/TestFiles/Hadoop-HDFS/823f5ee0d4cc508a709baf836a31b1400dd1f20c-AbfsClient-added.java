import org.apache.hadoop.fs.store.LogExactlyOnce;
import static org.apache.hadoop.fs.azurebfs.AbfsStatistic.RENAME_PATH_ATTEMPTS;
import static org.apache.hadoop.fs.azurebfs.contracts.services.AzureServiceErrorCode.RENAME_DESTINATION_PARENT_PATH_NOT_FOUND;
  /** logging the rename failure if metadata is in an incomplete state. */
  private static final LogExactlyOnce ABFS_METADATA_INCOMPLETE_RENAME_FAILURE =
      new LogExactlyOnce(LOG);

   * @param isMetadataIncompleteState was there a rename failure due to
   *                                  incomplete metadata state?
   * @return AbfsClientRenameResult result of rename operation indicating the
   * AbfsRest operation, rename recovery and incomplete metadata state failure.
  public AbfsClientRenameResult renamePath(
      final String sourceEtag,
      boolean isMetadataIncompleteState)
      incrementAbfsRenamePath();
      // AbfsClientResult contains the AbfsOperation, If recovery happened or
      // not, and the incompleteMetaDataState is true or false.
      // If we successfully rename a path and isMetadataIncompleteState was
      // true, then rename was recovered, else it didn't, this is why
      // isMetadataIncompleteState is used for renameRecovery(as the 2nd param).
      return new AbfsClientRenameResult(op, isMetadataIncompleteState, isMetadataIncompleteState);

        // ref: HADOOP-18242. Rename failure occurring due to a rare case of
        // tracking metadata being in incomplete state.
        if (op.getResult().getStorageErrorCode()
            .equals(RENAME_DESTINATION_PARENT_PATH_NOT_FOUND.getErrorCode())
            && !isMetadataIncompleteState) {
          //Logging
          ABFS_METADATA_INCOMPLETE_RENAME_FAILURE
              .info("Rename Failure attempting to resolve tracking metadata state and retrying.");

          // Doing a HEAD call resolves the incomplete metadata state and
          // then we can retry the rename operation.
          AbfsRestOperation sourceStatusOp = getPathStatus(source, false,
              tracingContext);
          isMetadataIncompleteState = true;
          // Extract the sourceEtag, using the status Op, and set it
          // for future rename recovery.
          AbfsHttpOperation sourceStatusResult = sourceStatusOp.getResult();
          String sourceEtagAfterFailure = extractEtagHeader(sourceStatusResult);
          renamePath(source, destination, continuation, tracingContext,
              sourceEtagAfterFailure, isMetadataIncompleteState);
        }
        // if we get out of the condition without a successful rename, then
        // it isn't metadata incomplete state issue.
        isMetadataIncompleteState = false;

      return new AbfsClientRenameResult(op, true, isMetadataIncompleteState);
  private void incrementAbfsRenamePath() {
    abfsCounters.incrementCounter(RENAME_PATH_ATTEMPTS, 1);
  }

