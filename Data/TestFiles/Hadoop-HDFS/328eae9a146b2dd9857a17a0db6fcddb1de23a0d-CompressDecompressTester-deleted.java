  public void test() throws InstantiationException, IllegalAccessException {
          Decompressor decompressor, byte[] rawData) {
        byte[] compressedResult = new byte[rawData.length];
        try {
          assertTrue(
              joiner.join(name, "compressor.needsInput before error !!!"),
              compressor.needsInput());
          assertTrue(
              compressor.getBytesWritten() == 0);
          compressor.setInput(rawData, 0, rawData.length);
          compressor.finish();
          while (!compressor.finished()) {
            cSize += compressor.compress(compressedResult, 0,
                compressedResult.length);
          }
          compressor.reset();

          assertTrue(
              joiner.join(name, "decompressor.needsInput() before error !!!"),
              decompressor.needsInput());
          decompressor.setInput(compressedResult, 0, cSize);
          assertFalse(
              joiner.join(name, "decompressor.needsInput() after error !!!"),
              decompressor.needsInput());
          while (!decompressor.finished()) {
            decompressedSize = decompressor.decompress(decompressedBytes, 0,
                decompressedBytes.length);
          }
          decompressor.reset();
          assertTrue(joiner.join(name, " byte size not equals error !!!"),
              decompressedSize == rawData.length);
          assertArrayEquals(
              joiner.join(name, " byte arrays not equals error !!!"), rawData,
              decompressedBytes);
        } catch (Exception ex) {
          fail(joiner.join(name, ex.getMessage()));
        Decompressor decompressor, byte[] originalRawData);
