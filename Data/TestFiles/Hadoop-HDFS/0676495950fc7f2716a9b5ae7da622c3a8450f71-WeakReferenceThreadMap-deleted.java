    WeakReference<V> ref = lookup(id);
    // Reference value could be set to null. Thus, ref.get() could return
    // null. Should be handled accordingly while using the returned value.
    if (ref != null && ref.get() == newVal) {
      return ref.get();
    return put(id, newVal);
