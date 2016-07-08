package com.verdant.jtools.proxy.center.discovery.listener;

import com.commons.proxy.center.route.IProxyRoute;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2015-2016 中盈优创
 * ProxyNodeCacheListener
 * Author: 龚健
 * Date: 2016/1/15
 */
public class ProxyNodeCacheListener implements PathChildrenCacheListener {

    final static Logger logger = LoggerFactory.getLogger(ProxyNodeCacheListener.class);

    public static Map<String, String> lostRecoveryMap = new HashMap<>();
    private IProxyRoute proxyRoute;
    private PathChildrenCache pathChildrenCache;

    public ProxyNodeCacheListener(IProxyRoute proxyRoute, PathChildrenCache pathChildrenCache) {
        this.proxyRoute = proxyRoute;
        this.pathChildrenCache = pathChildrenCache;
    }

    public void recovery(CuratorFramework client) {
        try {
            for (Map.Entry<String, String> node : lostRecoveryMap.entrySet()) {
                logger.debug("recovery node {}", node.toString());
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(node.getKey(), node.getValue().getBytes());
            }
        } catch (Exception e) {
            logger.error("recovery node fail", e);
        }
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
        switch (event.getType()) {
            case CHILD_ADDED: {
                proxyRoute.addOrUpdate(ZKPaths.getNodeFromPath(event.getData().getPath()), new String(event.getData().getData()));
                logger.debug("Proxy Node added: {}", ZKPaths.getNodeFromPath(event.getData().getPath()));
                break;
            }

            case CHILD_UPDATED: {
                proxyRoute.addOrUpdate(ZKPaths.getNodeFromPath(event.getData().getPath()), new String(event.getData().getData()));
                logger.debug("Proxy Node changed: {} ", ZKPaths.getNodeFromPath(event.getData().getPath()));
                break;
            }

            case CHILD_REMOVED: {
                proxyRoute.remove(ZKPaths.getNodeFromPath(event.getData().getPath()));
                logger.debug("Proxy Node removed: {}", ZKPaths.getNodeFromPath(event.getData().getPath()));
                break;
            }

            case CONNECTION_SUSPENDED: {
                logger.debug("Proxy CONNECTION_SUSPENDED");
                break;
                //暂停 web打断点超时触发
            }

            case CONNECTION_RECONNECTED: {
                logger.debug("Proxy CONNECTION_RECONNECTED");
                pathChildrenCache.rebuild();
                break;
            }

            case CONNECTION_LOST: {
                logger.debug("Proxy CONNECTION_LOST");
                recovery(curatorFramework);
                break;
            }

            case INITIALIZED: {
                logger.debug("Proxy INITIALIZED");
                break;
            }

        }
    }

}
