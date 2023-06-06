import org.apache.zookeeper.data.Stat;
        String nameSpace = "/" + zkClient.getNamespace();
        Stat stat = nullNsFw.checkExists().forPath(nameSpace);
        if (stat == null) {
          nullNsFw.create().creatingParentContainersIfNeeded().forPath(nameSpace);
        }
