import java.net.UnknownHostException;
import java.util.Calendar;
      ExtendedResolver resolver;
      try {
        resolver = new ExtendedResolver();
      } catch (UnknownHostException e) {
        LOG.error("Can not resolve DNS servers: ", e);
        return;
      }
            check.setTimeout(30);
      if (ResolverConfig.getCurrentConfig().servers() != null) {
        for (String server : ResolverConfig.getCurrentConfig()
            .servers()) {
          message.append(server);
          message.append(" ");
        }
        Iterator itor = zone.iterator();
          RRset rRset = (RRset) itor.next();
          Iterator sigs = rRset.sigs();
          if (!sigs.hasNext()) {
    Calendar cal = Calendar.getInstance();
    Date inception = cal.getTime();
    cal.add(Calendar.YEAR, 1);
    Date expiration = cal.getTime();
          Name cname = ((CNAMERecord) r).getAlias();
    Record[] records = response.getSectionArray(section);
    for (int i = 0; i < records.length; i++) {
      Record r = records[i];
        RRset[] rrsets = sr.answers();
        for (int i = 0; i < rrsets.length; i++) {
          addRRset(name, response, rrsets[i],
              Section.ANSWER, flags);
        response.getSectionArray(Section.QUESTION)[0]);
      Iterator it = rrset.rrs();
      while (it.hasNext()) {
        Record r = (Record) it.next();
      Iterator it = rrset.sigs();
      while (it.hasNext()) {
        Record r = (Record) it.next();
    Iterator it = zone.AXFR();
        RRset rrset = (RRset) it.next();
          tsig.applyStream(response, qtsig, first);
            Calendar cal = Calendar.getInstance();
            Date inception = cal.getTime();
            cal.add(Calendar.YEAR, 1);
            Date expiration = cal.getTime();
        Date inception,
        Date expiration) throws DNSSEC.DNSSECException {
