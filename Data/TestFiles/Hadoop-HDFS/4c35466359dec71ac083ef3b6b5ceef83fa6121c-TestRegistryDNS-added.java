import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
    List<Record> recs = assertDNSQuery("test1.root.dev.test.");
        ((ARecord) recs.get(0)).getAddress().getHostAddress());
        ((CNAMERecord) recs.get(0)).getTarget().toString());
    assertTrue("not an ARecord",
        recs.get(isSecure() ? 2 : 1) instanceof ARecord);
    assertTrue("not an SRV record", recs.get(0) instanceof SRVRecord);
    assertEquals("wrong port", 1026, ((SRVRecord) recs.get(0)).getPort());
        ((CNAMERecord) recs.get(0)).getTarget().toString());
    assertTrue("not an ARecord",
        recs.get(isSecure() ? 2 : 1) instanceof ARecord);
        ((CNAMERecord) recs.get(0)).getTarget().toString());
    assertTrue("not an ARecord",
        recs.get(isSecure() ? 2 : 1) instanceof ARecord);
    assertTrue("not an SRV record", recs.get(0) instanceof SRVRecord);
    assertEquals("wrong port", 1027, ((SRVRecord) recs.get(0)).getPort());
    List<Record> recs =
        ((ARecord) recs.get(0)).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs.get(0) instanceof ARecord);
    List<Record> recs = assertDNSQuery(
        ((ARecord) recs.get(0)).getAddress().getHostAddress());
    assertEquals("wrong ttl", 30L, recs.get(0).getTTL());
    assertTrue("not an ARecord", recs.get(0) instanceof ARecord);
    assertEquals("wrong ttl", 30L, recs.get(0).getTTL());
    List<Record> recs = assertDNSQuery(
        "19.0.17.172.in-addr.arpa.", Type.PTR, 1);
        ((PTRRecord) recs.get(0)).getTarget().toString());
    List<Record> recs = assertDNSQuery(
        "19.0.17.172.in-addr.arpa.", Type.PTR, 1);
        ((PTRRecord) recs.get(0)).getTarget().toString());
  private List<Record> assertDNSQuery(String lookup) throws IOException {
  private List<Record> assertDNSQuery(String lookup, int numRecs)
  private List<Record> assertDNSQuery(String lookup, int type, int numRecs)
    List<Record> recs = response.getSection(Section.ANSWER);
        isSecure() ? numRecs * 2 : numRecs, recs.size());
  private List<Record> assertDNSQueryNotNull(
      String lookup, int type, int answerCount) throws IOException {
    List<Record> recs = response.getSection(Section.ANSWER);
    assertEquals(answerCount, recs.size());
    assertEquals(type, recs.get(0).getType());
    Instant inception = Instant.now();
    Instant expiration = inception.plus(365, ChronoUnit.DAYS);
    List<Record> recs = assertDNSQuery(
        ((AAAARecord) recs.get(0)).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs.get(0) instanceof AAAARecord);
    List<Record> sectionArray = response.getSection(Section.AUTHORITY);
        sectionArray.size());
    List<Record> recs =
        ((ARecord) recs.get(0)).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs.get(0) instanceof ARecord);
        ((PTRRecord) recs.get(0)).getTarget().toString());
    recs = response.getSection(Section.ANSWER);
        ((PTRRecord) recs.get(0)).getTarget().toString());
    assertDNSQueryNotNull("mail.yahoo.com.", Type.CNAME, 1);
    assertDNSQueryNotNull(".", Type.NS, 13);
    List<Record> recs =
    assertTrue("not an ARecord", recs.get(0) instanceof ARecord);
    assertTrue("not an ARecord", recs.get(1) instanceof ARecord);
