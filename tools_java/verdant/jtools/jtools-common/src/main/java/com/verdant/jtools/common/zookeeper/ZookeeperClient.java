package com.verdant.jtools.common.zookeeper;

import com.verdant.jtools.common.pool.AbstractClient;
import com.verdant.jtools.common.pool.config.AbstractConfig;
import com.verdant.jtools.common.pool.config.ZookeeperConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by Administrator on 2016/6/6.
 */
public class ZookeeperClient extends AbstractClient<CuratorFramework> {

    public ZookeeperClient(AbstractConfig config) {
        super();
        if (config instanceof ZookeeperConfig) {
            ZookeeperConfig zconfig = (ZookeeperConfig) config;
            CuratorFramework executor = CuratorFrameworkFactory.newClient(
                    zconfig.getConnectString(),
                    zconfig.getSessionTimeoutMs(),
                    zconfig.getConnectionTimeoutMs(),
                    zconfig.getRetryPolicy());
            executor.start();
            this.setExecutor(executor);
        } else {

        }
    }

    @Override
    public void close() {
        this.getExecutor().close();
    }

    public void addNode(String nodePath, String nodeVal) throws Exception {
        this.getExecutor().checkExists().forPath(nodePath);
        this.getExecutor().create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(nodePath, nodeVal.getBytes());
    }
}
