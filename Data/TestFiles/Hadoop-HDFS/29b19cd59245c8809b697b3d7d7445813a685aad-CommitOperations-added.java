import org.apache.hadoop.fs.PathIOException;
import org.apache.hadoop.fs.s3a.impl.InternalConstants;
      if (numParts > InternalConstants.DEFAULT_UPLOAD_PART_COUNT_LIMIT) {
        // fail if the file is too big.
        // it would be possible to be clever here and recalculate the part size,
        // but this is not currently done.
        throw new PathIOException(destPath.toString(),
            String.format("File to upload (size %d)"
                + " is too big to be uploaded in parts of size %d",
                numParts, length));
      }
