  public void testSnappyCompressDecompress() {
    try {
      compressor.setInput(bytes, 0, bytes.length);
      assertTrue("SnappyCompressDecompress getBytesRead error !!!",
          compressor.getBytesRead() > 0);
      assertTrue(
          "SnappyCompressDecompress getBytesWritten before compress error !!!",
          compressor.getBytesWritten() == 0);

      byte[] compressed = new byte[BYTE_SIZE];
      int cSize = compressor.compress(compressed, 0, compressed.length);
      assertTrue(
          "SnappyCompressDecompress getBytesWritten after compress error !!!",
          compressor.getBytesWritten() > 0);

      SnappyDecompressor decompressor = new SnappyDecompressor(BYTE_SIZE);
      // set as input for decompressor only compressed data indicated with cSize
      decompressor.setInput(compressed, 0, cSize);
      byte[] decompressed = new byte[BYTE_SIZE];
      decompressor.decompress(decompressed, 0, decompressed.length);

      assertTrue("testSnappyCompressDecompress finished error !!!",
          decompressor.finished());
      Assert.assertArrayEquals(bytes, decompressed);
      compressor.reset();
      decompressor.reset();
      assertTrue("decompressor getRemaining error !!!",
          decompressor.getRemaining() == 0);
    } catch (Exception e) {
      fail("testSnappyCompressDecompress ex error!!!");
    }

