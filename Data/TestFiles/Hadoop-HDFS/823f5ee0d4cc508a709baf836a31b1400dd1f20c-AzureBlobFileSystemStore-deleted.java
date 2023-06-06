import org.apache.commons.lang3.tuple.Pair;
        final Pair<AbfsRestOperation, Boolean> pair =
                continuation, tracingContext, sourceEtag);
        AbfsRestOperation op = pair.getLeft();
        recovered |= pair.getRight();
  AbfsClient getClient() {
