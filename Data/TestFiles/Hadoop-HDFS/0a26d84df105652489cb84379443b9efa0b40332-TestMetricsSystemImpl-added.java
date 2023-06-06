    assertTrue("NumAllSinks should be 3",
        Iterables.contains(r.metrics(), new MetricGaugeInt(MsInfo.NumAllSinks, 3)));
