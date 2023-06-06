import org.apache.hadoop.fs.viewfs.ViewFileSystem;
import static org.apache.hadoop.fs.viewfs.Constants.*;

    /*
     * In HADOOP-18144, we changed getTrashRoot() in ViewFileSystem to return a
     * viewFS path, instead of a targetFS path. moveToTrash works for
     * ViewFileSystem now. ViewFileSystem will do path resolution internally by
     * itself.
     *
     * When localized trash flag is enabled:
     *    1). if fs is a ViewFileSystem, we can initialize Trash() with a
     *        ViewFileSystem object;
     *    2). When fs is not a ViewFileSystem, the only place we would need to
     *        resolve a path is for symbolic links. However, symlink is not
     *        enabled in Hadoop due to the complexity to support it
     *        (HADOOP-10019).
     */
    if (conf.getBoolean(CONFIG_VIEWFS_TRASH_FORCE_INSIDE_MOUNT_POINT,
        CONFIG_VIEWFS_TRASH_FORCE_INSIDE_MOUNT_POINT_DEFAULT)) {
      Trash trash = new Trash(fs, conf);
      return trash.moveToTrash(p);
    }

