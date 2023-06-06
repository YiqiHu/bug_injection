import com.amazonaws.SdkClientException;
      private Integer mockedS3ObjectIndex = 0;
        mockedS3ObjectIndex++;
        // open() -> lazySeek() -> reopen()
        //        -> getObject (mockedS3ObjectIndex=1) -> getObjectContent(objectInputStreamBad1)
        // read() -> objectInputStreamBad1 throws exception
        //        -> onReadFailure -> close wrappedStream
        //  -> retry(1) -> wrappedStream==null -> reopen -> getObject (mockedS3ObjectIndex=2)
        //        -> getObjectContent(objectInputStreamBad2)-> objectInputStreamBad2
        //        -> wrappedStream.read -> objectInputStreamBad2 throws exception
        //        -> onReadFailure -> close wrappedStream
        //  -> retry(2) -> wrappedStream==null -> reopen
        //        -> getObject (mockedS3ObjectIndex=3) throws exception
        //  -> retry(3) -> wrappedStream==null -> reopen -> getObject (mockedS3ObjectIndex=4)
        //        -> getObjectContent(objectInputStreamGood)-> objectInputStreamGood
        //        -> wrappedStream.read
        if (mockedS3ObjectIndex == 3) {
          throw new SdkClientException("Failed to get S3Object");
        }
