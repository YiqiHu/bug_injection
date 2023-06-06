import com.google.common.annotations.VisibleForTesting;


  /**
   * A configuration option for test use only: maximum
   * part count on block writes/uploads.
   * Value: {@value}.
   */
  @VisibleForTesting
  public static final String UPLOAD_PART_COUNT_LIMIT =
          "fs.s3a.internal.upload.part.count.limit";

  /**
   * Maximum entries you can upload in a single file write/copy/upload.
   * Value: {@value}.
   */
  public static final int DEFAULT_UPLOAD_PART_COUNT_LIMIT = 10000;
