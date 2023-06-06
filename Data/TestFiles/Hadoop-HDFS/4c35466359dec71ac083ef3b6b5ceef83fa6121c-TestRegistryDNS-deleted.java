import java.util.Calendar;
import java.util.Date;
    Record[] recs = assertDNSQuery("test1.root.dev.test.");
        ((ARecord) recs[0]).getAddress().getHostAddress());
        ((CNAMERecord) recs[0]).getTarget().toString());
    assertTrue("not an ARecord", recs[isSecure() ? 2 : 1] instanceof ARecord);
    assertTrue("not an SRV record", recs[0] instanceof SRVRecord);
    assertEquals("wrong port", 1026, ((SRVRecord) recs[0]).getPort());
        ((CNAMERecord) recs[0]).getTarget().toString());
    assertTrue("not an ARecord", recs[isSecure() ? 2 : 1] instanceof ARecord);
        ((CNAMERecord) recs[0]).getTarget().toString());
    assertTrue("not an ARecord", recs[isSecure() ? 2 : 1] instanceof ARecord);
    assertTrue("not an SRV record", recs[0] instanceof SRVRecord);
    assertEquals("wrong port", 1027, ((SRVRecord) recs[0]).getPort());
    Record[] recs =
        ((ARecord) recs[0]).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs[0] instanceof ARecord);
    Record[] recs = assertDNSQuery(
        ((ARecord) recs[0]).getAddress().getHostAddress());
    assertEquals("wrong ttl", 30L, recs[0].getTTL());
    assertTrue("not an ARecord", recs[0] instanceof ARecord);
    assertEquals("wrong ttl", 30L, recs[0].getTTL());
    Record[] recs = assertDNSQuery("19.0.17.172.in-addr.arpa.", Type.PTR, 1);
        ((PTRRecord) recs[0]).getTarget().toString());
    Record[] recs = assertDNSQuery("19.0.17.172.in-addr.arpa.", Type.PTR, 1);
        ((PTRRecord) recs[0]).getTarget().toString());
  private Record[] assertDNSQuery(String lookup) throws IOException {
  private Record[] assertDNSQuery(String lookup, int numRecs)
  Record[] assertDNSQuery(String lookup, int type, int numRecs)
    Record[] recs = response.getSectionArray(Section.ANSWER);
        isSecure() ? numRecs * 2 : numRecs, recs.length);
  Record[] assertDNSQueryNotNull(String lookup, int type, int answerCount)
      throws IOException {
    Record[] recs = response.getSectionArray(Section.ANSWER);
    assertEquals(answerCount, recs.length);
    assertEquals(recs[0].getType(), type);
    Calendar cal = Calendar.getInstance();
    Date inception = cal.getTime();
    cal.add(Calendar.YEAR, 1);
    Date expiration = cal.getTime();
    Record[] recs = assertDNSQuery(
        ((AAAARecord) recs[0]).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs[0] instanceof AAAARecord);
    Record[] sectionArray = response.getSectionArray(Section.AUTHORITY);
        sectionArray.length);
    Record[] recs =
        ((ARecord) recs[0]).getAddress().getHostAddress());
    assertTrue("not an ARecord", recs[0] instanceof ARecord);
        ((PTRRecord) recs[0]).getTarget().toString());
    recs = response.getSectionArray(Section.ANSWER);
        ((PTRRecord) recs[0]).getTarget().toString());
    Record[] recs =
        assertDNSQueryNotNull("mail.yahoo.com.", Type.CNAME, 1);
    Record[] recs =
        assertDNSQueryNotNull(".", Type.NS, 13);
    Record[] recs =
    assertTrue("not an ARecord", recs[0] instanceof ARecord);
    assertTrue("not an ARecord", recs[1] instanceof ARecord);
