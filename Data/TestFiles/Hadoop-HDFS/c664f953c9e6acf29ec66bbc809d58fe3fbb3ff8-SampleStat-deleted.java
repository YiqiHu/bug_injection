  private double a0, a1, s0, s1, total;
    a0 = s0 = 0.0;
    total = 0.0;
    a0 = s0 = 0.0;
    total = 0.0;
  void reset(long numSamples, double a0, double a1, double s0, double s1,
      double total, MinMax minmax) {
    this.numSamples = numSamples;
    this.a0 = a0;
    this.a1 = a1;
    this.s0 = s0;
    this.s1 = s1;
    this.total = total;
    this.minmax.reset(minmax);
    other.reset(numSamples, a0, a1, s0, s1, total, minmax);
   * @param x the partial sum
  public SampleStat add(long nSamples, double x) {
    total += x;
    if (numSamples == 1) {
      a0 = a1 = x;
      s0 = 0.0;
    }
    else {
      // The Welford method for numerical stability
      a1 = a0 + (x - a0) / numSamples;
      s1 = s0 + (x - a0) * (x - a1);
      a0 = a1;
      s0 = s1;
    }
    return total;
    return numSamples > 0 ? (total / numSamples) : 0.0;
    return numSamples > 1 ? s1 / (numSamples - 1) : 0.0;
