import static java.util.Objects.requireNonNull;

  /**
   * Get the value for the current thread, creating if needed.
   * @return an instance.
   */
  /**
   * Remove the reference for the current thread.
   * @return any reference value which existed.
   */
  /**
   * Get the current thread ID.
   * @return thread ID.
   */
  /**
   * Set the new value for the current thread.
   * @param newVal new reference to set for the active thread.
   * @return the previously set value, possibly null
   */
    requireNonNull(newVal);
    WeakReference<V> existingWeakRef = lookup(id);

    // The looked up reference could be one of
    // 1. null: nothing there
    // 2. valid but get() == null : reference lost by GC.
    // 3. different from the new value
    // 4. the same as the old value
    if (resolve(existingWeakRef) == newVal) {
      // case 4: do nothing, return the new value
      return newVal;
    } else {
      // cases 1, 2, 3: update the map and return the old value
      return put(id, newVal);
