    private volatile Set<String> terseExceptions = new HashSet<>();
    private volatile Set<String> suppressedExceptions = new HashSet<>();
      // Thread-safe replacement of terseExceptions.
      terseExceptions = addExceptions(terseExceptions, exceptionClass);
      // Thread-safe replacement of suppressedExceptions.
      suppressedExceptions = addExceptions(
          suppressedExceptions, exceptionClass);
    /**
     * Return a new set containing all the exceptions in exceptionsSet
     * and exceptionClass.
     * @return
     */
    private static Set<String> addExceptions(
        final Set<String> exceptionsSet, Class<?>[] exceptionClass) {
      // Make a copy of the exceptionSet for performing modification
      final HashSet<String> newSet = new HashSet<>(exceptionsSet);

      // Add all class names into the HashSet
      for (Class<?> name : exceptionClass) {
        newSet.add(name.toString());
      }

      return Collections.unmodifiableSet(newSet);
    }
