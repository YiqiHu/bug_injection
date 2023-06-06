import com.amazonaws.AmazonClientException;
   * @throws IOException Problems opening the destination for upload
   * or initializing the upload.
      final S3ADataBlocks.BlockUploadData uploadData = block.startUpload();
      final UploadPartRequest request =
          writeOperationHelper.newUploadPartRequest(
              key,
              uploadId,
              currentPartNumber,
              size,
              uploadData.getUploadStream(),
              uploadData.getFile(),
              0L);

        LOG.debug("Cancelling futures");
        for (ListenableFuture<PartETag> future : partETagsFutures) {
          future.cancel(true);
        }
      int retryCount = 0;
      AmazonClientException lastException;
