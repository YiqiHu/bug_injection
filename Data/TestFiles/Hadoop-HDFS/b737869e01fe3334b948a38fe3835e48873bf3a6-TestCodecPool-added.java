import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.hadoop.io.compress.zlib.BuiltInGzipCompressor;
import org.apache.hadoop.io.compress.zlib.BuiltInGzipDecompressor;
import org.apache.hadoop.test.LambdaTestUtils;

  @Test(timeout = 10000)
  public void testDoNotPoolCompressorNotUseableAfterReturn() throws Exception {

    final GzipCodec gzipCodec = new GzipCodec();
    gzipCodec.setConf(new Configuration());

    // BuiltInGzipCompressor is an explicit example of a Compressor with the @DoNotPool annotation
    final Compressor compressor = new BuiltInGzipCompressor(new Configuration());
    CodecPool.returnCompressor(compressor);

    final CompressionOutputStream outputStream =
            gzipCodec.createOutputStream(new ByteArrayOutputStream(), compressor);
    LambdaTestUtils.intercept(
            AlreadyClosedException.class,
            "compress called on closed compressor",
            "Compressor from Codec with @DoNotPool should not be " +
                    "useable after returning to CodecPool",
        () -> outputStream.write(1));
  }

  @Test(timeout = 10000)
  public void testDoNotPoolDecompressorNotUseableAfterReturn() throws Exception {

    final GzipCodec gzipCodec = new GzipCodec();
    gzipCodec.setConf(new Configuration());

    final Random random = new Random();
    final byte[] bytes = new byte[1024];
    random.nextBytes(bytes);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (OutputStream outputStream = gzipCodec.createOutputStream(baos)) {
      outputStream.write(bytes);
    }

    final byte[] gzipBytes = baos.toByteArray();
    final ByteArrayInputStream bais = new ByteArrayInputStream(gzipBytes);

    // BuiltInGzipDecompressor is an explicit example of a Decompressor
    // with the @DoNotPool annotation
    final Decompressor decompressor = new BuiltInGzipDecompressor();
    CodecPool.returnDecompressor(decompressor);

    final CompressionInputStream inputStream = gzipCodec.createInputStream(bais, decompressor);
    LambdaTestUtils.intercept(
            AlreadyClosedException.class,
            "decompress called on closed decompressor",
            "Decompressor from Codec with @DoNotPool should not be " +
                    "useable after returning to CodecPool",
        () -> inputStream.read());
  }
