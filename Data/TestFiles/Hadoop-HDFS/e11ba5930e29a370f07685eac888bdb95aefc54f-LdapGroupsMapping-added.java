import org.apache.hadoop.classification.VisibleForTesting;
  @VisibleForTesting
  Set<String> lookupGroup(SearchResult result, DirContext c,
        // In order to force the fallback, we need to reset groups collection.
        groups.clear();
