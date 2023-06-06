  public static class MountPoint<T> {

    /**
     * Returns the source of mount point.
     * @return The source
     */
    public String getSource() {
      return this.src;
    }

    /**
     * Returns the target link.
     * @return The target INode link
     */
    public INodeLink<T> getTarget() {
      return this.target;
    }
  public static class INodeLink<T> extends INode<T> {
    public Path getTargetLink() {
  public List<MountPoint<T>> getMountPoints() {
