import org.apache.commons.lang3.tuple.Pair;
   * @return pair of (the rename operation, flag indicating recovery took place)
  public Pair<AbfsRestOperation, Boolean> renamePath(
      final String sourceEtag)
      return Pair.of(op, false);
      return Pair.of(op, true);
