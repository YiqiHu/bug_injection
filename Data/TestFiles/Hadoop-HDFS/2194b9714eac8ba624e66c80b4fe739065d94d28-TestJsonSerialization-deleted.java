      assertEquals(source, serDeser.load(fs, tempPath));
      LambdaTestUtils.intercept(EOFException.class,
