import org.apache.hadoop.fs.PathIOException;
   * @throws IOException Problems opening the destination for upload,
   * initializing the upload, or if a previous operation has failed.
      // the operation failed.
      // if this happened during a multipart upload, abort the
      // operation, so as to not leave (billable) data
      // pending on the bucket
      if (multiPartUpload != null) {
        multiPartUpload.abort();
      }
    /**
     * Any IOException raised during block upload.
     * if non-null, then close() MUST NOT complete
     * the file upload.
     */
    private IOException blockUploadFailure;

    /**
     * A block upload has failed.
     * Recorded it if there has been no previous failure.
     * @param e error
     */
    public void noteUploadFailure(final IOException e) {
      if (blockUploadFailure == null) {
        blockUploadFailure = e;
      }
    }

    /**
     * If there is a block upload failure -throw it.
     * @throws IOException if one has already been caught.
     */
    public void maybeRethrowUploadFailure() throws IOException {
      if (blockUploadFailure != null) {
        throw blockUploadFailure;
      }
    }

     * @throws PathIOException if too many blocks were written
      maybeRethrowUploadFailure();
      final UploadPartRequest request;
      final S3ADataBlocks.BlockUploadData uploadData;
      try {
        uploadData = block.startUpload();
        request = writeOperationHelper.newUploadPartRequest(
            key,
            uploadId,
            currentPartNumber,
            size,
            uploadData.getUploadStream(),
            uploadData.getFile(),
            0L);
      } catch (IOException e) {
        // failure to start the upload.
        noteUploadFailure(e);
        throw e;
      }
            } catch (IOException e) {
              // save immediately.
              noteUploadFailure(e);
              throw e;
    /**
     * Cancel all active uploads.
     */
    private void cancelAllActiveFutures() {
      LOG.debug("Cancelling futures");
      for (ListenableFuture<PartETag> future : partETagsFutures) {
        future.cancel(true);
      }
    }

      maybeRethrowUploadFailure();
      LOG.debug("Aborting upload");
      cancelAllActiveFutures();
