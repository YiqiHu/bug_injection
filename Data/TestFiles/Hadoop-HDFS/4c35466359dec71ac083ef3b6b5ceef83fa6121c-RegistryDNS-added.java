import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
      ExtendedResolver resolver = new ExtendedResolver();
            check.setTimeout(Duration.ofSeconds(30));
      for (InetSocketAddress address :
          ResolverConfig.getCurrentConfig().servers()) {
        message.append(address);
        message.append(" ");
        Iterator<RRset> itor = zone.iterator();
          RRset rRset = itor.next();
          if (!rRset.sigs().isEmpty()) {
    Instant inception = Instant.now();
    Instant expiration = inception.plus(365, ChronoUnit.DAYS);
          Name cname = r.getName();
    for (Record r : response.getSection(section)) {
        List<RRset> rrsets = sr.answers();
        for (RRset rrset : rrsets) {
          addRRset(name, response, rrset, Section.ANSWER, flags);
        response.getSection(Section.QUESTION).get(0));
      for (Record r : rrset.rrs()) {
      for (Record r : rrset.sigs()) {
    Iterator<RRset> it = zone.AXFR();
        RRset rrset = it.next();
          tsig.apply(response, qtsig, first);
            Instant inception = Instant.now();
            Instant expiration = inception.plus(365, ChronoUnit.DAYS);
        Instant inception,
        Instant expiration) throws DNSSEC.DNSSECException {
