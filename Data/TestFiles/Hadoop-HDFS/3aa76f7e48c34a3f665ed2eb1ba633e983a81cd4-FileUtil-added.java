import org.apache.commons.io.FileUtils;
    if (!FileUtils.isSymlink(dir) && !fullyDeleteContents(dir, tryGrantPermissions)) {
