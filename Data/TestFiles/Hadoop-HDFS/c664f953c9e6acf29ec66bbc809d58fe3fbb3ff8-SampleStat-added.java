  private double mean, s;
    mean = 0.0;
    s = 0.0;
    mean = 0.0;
    s = 0.0;
  void reset(long numSamples1, double mean1, double s1, MinMax minmax1) {
    numSamples = numSamples1;
    mean = mean1;
    s = s1;
    minmax.reset(minmax1);
    other.reset(numSamples, mean, s, minmax);
   * @param xTotal the partial sum
  public SampleStat add(long nSamples, double xTotal) {
    // use the weighted incremental version of Welford's algorithm to get
    // numerical stability while treating the samples as being weighted
    // by nSamples
    // see https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance

    double x = xTotal / nSamples;
    double meanOld = mean;

    mean += ((double) nSamples / numSamples) * (x - meanOld);
    s += nSamples * (x - meanOld) * (x - mean);
    return mean * numSamples;
    return numSamples > 0 ? mean : 0.0;
    return numSamples > 1 ? s / (numSamples - 1) : 0.0;
