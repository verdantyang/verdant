package com.verdant.jtools.integration.zookeeper.client;

import com.verdant.jtools.common.pool.client.AbstractClient;
import com.verdant.jtools.common.pool.config.IConfig;
import com.verdant.jtools.integration.zookeeper.config.CuratorConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

/**
 * Curator Client操作
 *
 * @author verdant
 * @since 2016/06/02
 */
public class CuratorClient extends AbstractClient<CuratorFramework> {

    public CuratorClient(IConfig config) {
        super();
        if (config instanceof CuratorConfig) {
            CuratorConfig zconfig = (CuratorConfig) config;
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

    public void addNode(String nodePath, String nodeVal, CuratorClient client) {
        if (client == null)
            client = this;
        try {
            client.getExecutor().checkExists().forPath(nodePath);
            client.getExecutor().create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(nodePath, nodeVal.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
