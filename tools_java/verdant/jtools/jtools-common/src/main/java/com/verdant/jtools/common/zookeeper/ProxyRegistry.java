package com.verdant.jtools.common.zookeeper;

import com.verdant.jtools.common.pool.AbstractClient;
import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.common.pool.IFactory;
import com.verdant.jtools.common.pool.IPool;
import com.verdant.jtools.common.pool.config.ZookeeperConfig;
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
public class ProxyRegistry {

    final static Logger logger = LoggerFactory.getLogger(ProxyRegistry.class);
    String nodePath = "/rps/dynamic/epl";
    String nodeVal = "abc";

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
//            CuratorFramework client = builder.connectString("192.168.11.56:2180")
//                    .sessionTimeoutMs(30000)
//                    .connectionTimeoutMs(30000)
//                    .canBeReadOnly(false)
//                    .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
//                    .namespace(namespace)
//                    .defaultData(null)
//                    .build();
//            client.start();


//            CuratorFramework client = manager.get("default");
//            CuratorFramework client = AbstractPool.getInstance();
//            client.checkExists().forPath(nodePath);
//            client.create().creatingParentsIfNeeded()
//                    .withMode(CreateMode.EPHEMERAL)
//                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                    .forPath(nodePath, nodeVal.getBytes());
//            ProxyNodeCacheListener.lostRecoveryMap.put(nodePath,nodeVal);
            System.out.println(nodePath);
            logger.debug("register proxy zk path {}", nodePath);

        } catch (Exception e) {
            e.printStackTrace();
//            throw new ServiceException(e);
        }
    }

    public void usePool1() throws Exception {
        ZookeeperUtils.getInstance().addNode(nodePath, nodeVal);
        System.out.println(nodePath);
    }

    public void usePool2() {
        IPool<ZookeeperClient> pool = new IPool.Builder<ZookeeperClient>(new IFactory() {
            @Override
            public ZookeeperClient create() {
                ZookeeperClient client = new ZookeeperClient(new ZookeeperConfig());
                return client;
            }
        }).softReferences().build();
        String value = pool.run(new ICallback<String, AbstractClient>() {
            @Override
            public String execute(AbstractClient client) {
                if (client instanceof ZookeeperClient)
                    client.getExecutor().checkExists().forPath(nodePath);
                client.getExecutor().create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(nodePath, nodeVal.getBytes());
                return "abc";
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ProxyRegistry usage = new ProxyRegistry();
//        usage.register();
        usage.usePool1();
    }
}
