package com.verdant.jtools.common.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ZK实现的代理注册
 * Copyright (C), 2015-2016 中盈优创
 * ZookeeperProxyService
 * Date: 2015/10/13
 */
public class ZookeeperProxyRegistry {

    final static Logger logger = LoggerFactory.getLogger(ZookeeperProxyRegistry.class);

    /**
     * 注册服务
     */
    public void register() {
        ZookeeperConfig zkcfg = new ZookeeperConfig();
        Map<String, ZookeeperConfig> mgcfg = new HashMap<>();
        mgcfg.put("default", zkcfg);
        ZookeeperManager manager = new ZookeeperManager();
        manager.setResources(mgcfg);
        manager.start();
        try {
//            Platform platform = proxyConfig.getPlatformInfo();
//            platform.setServices(services);
//            CuratorFramework client = builder.connectString("192.168.11.56:2180")
//                    .sessionTimeoutMs(30000)
//                    .connectionTimeoutMs(30000)
//                    .canBeReadOnly(false)
//                    .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
//                    .namespace(namespace)
//                    .defaultData(null)
//                    .build();
//            client.start();
            String nodePath = "/rps/dynamic/epl";
            String nodeVal = "abc";
//            CuratorFramework client = manager.get("default");
            CuratorFramework client = AbstractPool.getInstance();
            client.checkExists().forPath(nodePath);
//            client.
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(nodePath, nodeVal.getBytes());
//            ProxyNodeCacheListener.lostRecoveryMap.put(nodePath,nodeVal);
            System.out.println(nodePath);
            logger.debug("register proxy zk path {}",nodePath);

        } catch (Exception e) {
            e.printStackTrace();
//            throw new ServiceException(e);
        }
    }

    public static void main(String[] args) {
        ZookeeperProxyRegistry usage = new ZookeeperProxyRegistry();
        usage.register();
    }

}
