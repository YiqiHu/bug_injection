import java.util.Optional;
   * Returns the parent of a path or null if at root. Better alternative is
   * {@link #getOptionalParentPath()} to handle nullable value for root path.
   *
    return getParentUtil();
  }

  /**
   * Returns the parent of a path as {@link Optional} or
   * {@link Optional#empty()} i.e an empty Optional if at root.
   *
   * @return Parent of path wrappen in {@link Optional}.
   * {@link Optional#empty()} i.e an empty Optional if at root.
   */
  public Optional<Path> getOptionalParentPath() {
    return Optional.ofNullable(getParentUtil());
  }

  private Path getParentUtil() {
        (lastSlash == start && path.length() == start + 1)) { // at root
    if (lastSlash == -1) {
      parent = path.substring(0, lastSlash == start ? start + 1 : lastSlash);
