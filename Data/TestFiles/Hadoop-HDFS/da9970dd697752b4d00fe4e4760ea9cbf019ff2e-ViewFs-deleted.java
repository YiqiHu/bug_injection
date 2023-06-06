    private Path src;       // the src of the mount
    private URI[] targets; //  target of the mount; Multiple targets imply mergeMount
    MountPoint(Path srcPath, URI[] targetURIs) {
    URI[] getTargets() {
