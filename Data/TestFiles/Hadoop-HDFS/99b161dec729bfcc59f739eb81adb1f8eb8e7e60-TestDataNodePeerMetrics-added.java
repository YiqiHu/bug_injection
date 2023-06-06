    String json = peerMetrics.dumpSendPacketDownstreamAvgInfoAsJson();
    for (String peerAddr : peerAddrList) {
      assertThat(json, containsString(peerAddr));
    }
    json = peerMetrics.dumpSendPacketDownstreamAvgInfoAsJson();
    assertEquals("{}", json);
    json = peerMetrics.dumpSendPacketDownstreamAvgInfoAsJson();
    for (String peerAddr : peerAddrList) {
      assertThat(json, containsString(peerAddr));
    }
