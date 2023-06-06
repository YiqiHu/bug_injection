import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.fs.store.LogExactlyOnce;
  private static final Logger LOG =
      LoggerFactory.getLogger(WeakReferenceMap.class);

  /**
   * Log to report loss of a reference during the create phase, which
   * is believed to be a cause of HADOOP-18456.
   */
  private final LogExactlyOnce referenceLostDuringCreation = new LogExactlyOnce(LOG);

    final WeakReference<V> currentWeakRef = lookup(key);
    // resolve it, after which if not null, we have a strong reference
    V strongVal = resolve(currentWeakRef);
    if (strongVal != null) {
      return  strongVal;
    // here, either currentWeakRef was null, or its reference was GC'd.
    if (currentWeakRef != null) {
      // garbage collection removed the reference.

      // explicitly remove the weak ref from the map if it has not
      // been updated by this point
      // this is here just for completeness.
      map.remove(key, currentWeakRef);

      // log/report the loss.

    // create a new value and add it to the map
   * <p>
   * If that race does occur, it will be logged the first time it happens
   * for this specific map instance.
   * <p>
   * HADOOP-18456 highlighted the risk of a concurrent GC resulting a null
   * value being retrieved and so returned.
   * To prevent this:
   * <ol>
   *   <li>A strong reference is retained to the newly created instance
   *       in a local variable.</li>
   *   <li>That variable is used after the resolution process, to ensure
   *       the JVM doesn't consider it "unreachable" and so eligible for GC.</li>
   *   <li>A check is made for the resolved reference being null, and if so,
   *       the put() is repeated</li>
   * </ol>
   * @return the created value
    /*
     Get a strong ref so even if a GC happens in this method the reference is not lost.
     It is NOT enough to have a reference in a field, it MUST be used
     so as to ensure the reference isn't optimized away prematurely.
     "A reachable object is any object that can be accessed in any potential continuing
      computation from any live thread."
    */

    final V strongRef = requireNonNull(factory.apply(key),
        "factory returned a null instance");
    V resolvedStrongRef;
    do {
      WeakReference<V> newWeakRef = new WeakReference<>(strongRef);

      // put it in the map
      map.put(key, newWeakRef);

      // get it back from the map
      WeakReference<V> retrievedWeakRef = map.get(key);
      // resolve that reference, handling the situation where somehow it was removed from the map
      // between the put() and the get()
      resolvedStrongRef = resolve(retrievedWeakRef);
      if (resolvedStrongRef == null) {
        referenceLostDuringCreation.warn("reference to %s lost during creation", key);
        noteLost(key);
      }
    } while (resolvedStrongRef == null);

    // note if there was any change in the reference.
    // as this forces strongRef to be kept in scope
    if (strongRef != resolvedStrongRef) {
      LOG.debug("Created instance for key {}: {} overwritten by {}",
          key, strongRef, resolvedStrongRef);
    }

    return resolvedStrongRef;
  protected V resolve(WeakReference<V> r) {
    // increment local counter
