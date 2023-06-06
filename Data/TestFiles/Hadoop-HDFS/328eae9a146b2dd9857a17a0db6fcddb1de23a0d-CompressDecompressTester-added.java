  public void test() throws Exception {
          Decompressor decompressor, byte[] rawData) throws Exception {
        // Snappy compression can increase data size
        int maxCompressedLength = 32 + rawData.length + rawData.length/6;
        byte[] compressedResult = new byte[maxCompressedLength];
        assertTrue(
            joiner.join(name, "compressor.needsInput before error !!!"),
            compressor.needsInput());
        assertEquals(
            0, compressor.getBytesWritten());
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
        decompressor.reset();
        assertEquals(joiner.join(name, " byte size not equals error !!!"),
            rawData.length, decompressedSize);
        assertArrayEquals(
            joiner.join(name, " byte arrays not equals error !!!"), rawData,
            decompressedBytes);
        Decompressor decompressor, byte[] originalRawData) throws Exception;
