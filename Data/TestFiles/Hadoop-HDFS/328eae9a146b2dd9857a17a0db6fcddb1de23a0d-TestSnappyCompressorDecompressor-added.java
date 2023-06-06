import static org.assertj.core.api.Assertions.assertThat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  public static final Logger LOG =
      LoggerFactory.getLogger(TestSnappyCompressorDecompressor.class);

  public void testSnappyCompressDecompress() throws Exception {
    compressor.setInput(bytes, 0, bytes.length);
    assertTrue("SnappyCompressDecompress getBytesRead error !!!",
        compressor.getBytesRead() > 0);
    assertEquals(
        "SnappyCompressDecompress getBytesWritten before compress error !!!",
        0, compressor.getBytesWritten());

    // snappy compression may increase data size.
    // This calculation comes from "Snappy::MaxCompressedLength(size_t)"
    int maxSize = 32 + BYTE_SIZE + BYTE_SIZE / 6;
    byte[] compressed = new byte[maxSize];
    int cSize = compressor.compress(compressed, 0, compressed.length);
    LOG.info("input size: {}", BYTE_SIZE);
    LOG.info("compressed size: {}", cSize);
    assertTrue(
        "SnappyCompressDecompress getBytesWritten after compress error !!!",
        compressor.getBytesWritten() > 0);

    SnappyDecompressor decompressor = new SnappyDecompressor();
    // set as input for decompressor only compressed data indicated with cSize
    decompressor.setInput(compressed, 0, cSize);
    byte[] decompressed = new byte[BYTE_SIZE];
    decompressor.decompress(decompressed, 0, decompressed.length);

    assertTrue("testSnappyCompressDecompress finished error !!!",
        decompressor.finished());
    Assert.assertArrayEquals(bytes, decompressed);
    compressor.reset();
    decompressor.reset();
    assertEquals("decompressor getRemaining error !!!",
        0, decompressor.getRemaining());

  @Test
  // The buffer size is smaller than the input.
  public void testSnappyCompressDecompressWithSmallBuffer() throws Exception {
    int inputSize = 1024 * 50;
    int bufferSize = 512;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[bufferSize];
    byte[] input = BytesGenerator.get(inputSize);

    SnappyCompressor compressor = new SnappyCompressor();
    compressor.setInput(input, 0, inputSize);
    compressor.finish();
    while (!compressor.finished()) {
      int len = compressor.compress(buffer, 0, buffer.length);
      out.write(buffer, 0, len);
    }
    byte[] compressed = out.toByteArray();
    assertThat(compressed).hasSizeGreaterThan(0);
    out.reset();

    SnappyDecompressor decompressor = new SnappyDecompressor();
    decompressor.setInput(compressed, 0, compressed.length);
    while (!decompressor.finished()) {
      int len = decompressor.decompress(buffer, 0, buffer.length);
      out.write(buffer, 0, len);
    }
    byte[] decompressed = out.toByteArray();

    assertThat(decompressed).isEqualTo(input);
  }

