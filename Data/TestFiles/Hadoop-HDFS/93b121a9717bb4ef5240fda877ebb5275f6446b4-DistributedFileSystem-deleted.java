   * Note that this operation is not atomic for a large directory.
   * The entries of a directory may be fetched from NameNode multiple times.
   * It only guarantees that  each name occurs once if a directory
   * undergoes changes between the calls.
