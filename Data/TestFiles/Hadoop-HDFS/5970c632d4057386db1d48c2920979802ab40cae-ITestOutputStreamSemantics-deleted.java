import static org.apache.hadoop.fs.contract.ContractTestUtils.assertCapabilities;
      assertCapabilities(stream,
          new String[]{
            StreamCapabilities.HFLUSH,
            StreamCapabilities.HSYNC,
            StreamCapabilities.DROPBEHIND,
            StreamCapabilities.READAHEAD,
            StreamCapabilities.UNBUFFER},
          null);
          StreamCapabilities.HSYNC,
