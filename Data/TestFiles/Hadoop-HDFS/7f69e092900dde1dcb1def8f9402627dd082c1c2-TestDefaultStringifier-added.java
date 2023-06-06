import static org.apache.hadoop.test.LambdaTestUtils.intercept;
  public void testStoreLoadArray() throws Exception {
    intercept(IndexOutOfBoundsException.class, () ->
        DefaultStringifier.storeArray(conf, new Integer[] {}, keyName));
