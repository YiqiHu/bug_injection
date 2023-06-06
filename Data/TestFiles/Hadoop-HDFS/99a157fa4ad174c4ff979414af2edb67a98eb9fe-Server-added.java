import java.util.stream.Collectors;

    private final Set<String> terseExceptions =
        ConcurrentHashMap.newKeySet();
    private final Set<String> suppressedExceptions =
        ConcurrentHashMap.newKeySet();
      terseExceptions.addAll(Arrays
          .stream(exceptionClass)
          .map(Class::toString)
          .collect(Collectors.toSet()));
      suppressedExceptions.addAll(Arrays
          .stream(exceptionClass)
          .map(Class::toString)
          .collect(Collectors.toSet()));
