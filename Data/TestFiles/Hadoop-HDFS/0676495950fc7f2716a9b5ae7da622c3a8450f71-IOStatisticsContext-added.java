import static java.util.Objects.requireNonNull;

    // the null check is just a safety check to highlight exactly where a null value would
    // be returned if HADOOP-18456 has resurfaced.
    return requireNonNull(
        IOStatisticsContextIntegration.getCurrentIOStatisticsContext(),
        "Null IOStatisticsContext");
