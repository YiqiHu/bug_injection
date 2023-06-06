    CopyListingFileStatus dstStatus = new CopyListingFileStatus(fs.getFileStatus(dst));
    Assert.assertTrue(srcStatus.getPermission().equals(dstStatus.getPermission()));
    Assert.assertTrue(srcStatus.getOwner().equals(dstStatus.getOwner()));
    Assert.assertTrue(srcStatus.getGroup().equals(dstStatus.getGroup()));
    Assert.assertTrue(srcStatus.getAccessTime() == dstStatus.getAccessTime());
    Assert.assertTrue(srcStatus.getModificationTime() == dstStatus.getModificationTime());
    Assert.assertTrue(srcStatus.getReplication() == dstStatus.getReplication());
    Assert.assertEquals("getPermission", srcStatus.getPermission(),
        dstStatus.getPermission());
    Assert.assertEquals("Owner", srcStatus.getOwner(), dstStatus.getOwner());
    Assert.assertEquals("Group", srcStatus.getGroup(), dstStatus.getGroup());
    Assert.assertEquals("AccessTime", srcStatus.getAccessTime(),
        dstStatus.getAccessTime());
    Assert.assertEquals("ModificationTime", srcStatus.getModificationTime(),
        dstStatus.getModificationTime());
    Assert.assertEquals("Replication", srcStatus.getReplication(),
        dstStatus.getReplication());
    Assert.assertFalse(srcStatus.getPermission().equals(dstStatus.getPermission()));
    Assert.assertFalse(srcStatus.getOwner().equals(dstStatus.getOwner()));
    Assert.assertFalse(srcStatus.getGroup().equals(dstStatus.getGroup()));
    Assert.assertFalse(srcStatus.getAccessTime() == dstStatus.getAccessTime());
    Assert.assertFalse(srcStatus.getModificationTime() == dstStatus.getModificationTime());
    Assert.assertFalse(srcStatus.getReplication() == dstStatus.getReplication());
    CopyListingFileStatus f2Status = new CopyListingFileStatus(fs.getFileStatus(f2));
    Assert.assertTrue(srcStatus.getPermission().equals(f2Status.getPermission()));
    Assert.assertTrue(srcStatus.getOwner().equals(f2Status.getOwner()));
    Assert.assertTrue(srcStatus.getGroup().equals(f2Status.getGroup()));
    Assert.assertTrue(srcStatus.getAccessTime() == f2Status.getAccessTime());
    Assert.assertTrue(srcStatus.getModificationTime() == f2Status.getModificationTime());
    Assert.assertTrue(srcStatus.getReplication() == f2Status.getReplication());
    CopyListingFileStatus f0Status = new CopyListingFileStatus(fs.getFileStatus(f0));
    Assert.assertTrue(srcStatus.getPermission().equals(f0Status.getPermission()));
    Assert.assertTrue(srcStatus.getOwner().equals(f0Status.getOwner()));
    Assert.assertTrue(srcStatus.getGroup().equals(f0Status.getGroup()));
    Assert.assertTrue(srcStatus.getAccessTime() == f0Status.getAccessTime());
    Assert.assertTrue(srcStatus.getModificationTime() == f0Status.getModificationTime());
    Assert.assertTrue(srcStatus.getReplication() == f0Status.getReplication());
