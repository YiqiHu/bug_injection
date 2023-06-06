    final WeakReference<V> current = lookup(key);
    V val = resolve(current);
    if (val != null) {
      return  val;
    // here, either no ref, or the value is null
    if (current != null) {
   * @return the value
    WeakReference<V> newRef = new WeakReference<>(
        requireNonNull(factory.apply(key)));
    map.put(key, newRef);
    return map.get(key).get();
  private V resolve(WeakReference<V> r) {
    // incrment local counter
