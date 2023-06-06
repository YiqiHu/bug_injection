import java.util.concurrent.atomic.AtomicLong;
import org.apache.hadoop.fs.impl.WeakReferenceThreadMap;
import static org.apache.hadoop.test.LambdaTestUtils.intercept;

 * Test {@link WeakReferenceMap} and {@link WeakReferenceThreadMap}.
  /**
   * It is an error to have a factory which returns null.
   */
  @Test
  public void testFactoryReturningNull() throws Throwable {
    referenceMap = new WeakReferenceMap<>(
        (k) -> null,
            null);
    intercept(NullPointerException.class, () ->
        referenceMap.get(0));
  }

  /**
   * Test the WeakReferenceThreadMap extension.
   */
  @Test
  public void testWeakReferenceThreadMapAssignment()
      throws Throwable {

    // counters foor the callbacks
    final AtomicLong created = new AtomicLong();
    final AtomicLong lost = new AtomicLong();

    WeakReferenceThreadMap<String> threadMap = new WeakReferenceThreadMap<>(
        id -> "Entry for thread ID " + id + " (" + created.incrementAndGet() + ")",
        id -> lost.incrementAndGet());

    Assertions.assertThat(threadMap.setForCurrentThread("hello"))
        .describedAs("current thread map value on first set")
        .isNull();

    // second attempt returns itself
    Assertions.assertThat(threadMap.setForCurrentThread("hello"))
        .describedAs("current thread map value on second set")
        .isEqualTo("hello");

    // it is forbidden to explicitly set to null via the set() call.
    intercept(NullPointerException.class, () ->
        threadMap.setForCurrentThread(null));

    // the map is unchanged
    Assertions.assertThat(threadMap.getForCurrentThread())
        .describedAs("current thread map value")
        .isEqualTo("hello");

    // remove the value and assert what the removed entry was
    Assertions.assertThat(threadMap.removeForCurrentThread())
        .describedAs("removed thread map value")
        .isEqualTo("hello");

    // remove the value again; this time the removed value is null
    Assertions.assertThat(threadMap.removeForCurrentThread())
        .describedAs("removed thread map value on second call")
        .isNull();

    // lookup will return a new instance created by the factory
    long c1 = created.get();
    String dynamicValue = threadMap.getForCurrentThread();
    Assertions.assertThat(dynamicValue)
        .describedAs("dynamically created thread map value")
        .startsWith("Entry for thread ID")
        .contains("(" + (c1 + 1) + ")");

    // and we can overwrite that
    Assertions.assertThat(threadMap.setForCurrentThread("hello2"))
        .describedAs("value before the thread entry is changed")
        .isEqualTo(dynamicValue);

    // simulate a weak gc
    long threadId = threadMap.currentThreadId();
    threadMap.put(threadId, null);
    String updated = threadMap.getForCurrentThread();
    Assertions.assertThat(lost.get())
        .describedAs("lost count")
        .isEqualTo(1);
    Assertions.assertThat(updated)
        .describedAs("dynamically created thread map value")
        .startsWith("Entry for thread ID")
        .contains("(" + (c1 + 2) + ")");
  }

   * @param val expected value
        .describedAs("map entry of key %d should be present", key)
