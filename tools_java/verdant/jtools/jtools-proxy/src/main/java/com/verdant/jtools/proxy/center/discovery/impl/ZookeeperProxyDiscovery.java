package com.verdant.jtools.proxy.center.discovery.impl;

import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.config.BaseProxyConfig;
import com.commons.proxy.center.config.ZookeeperProxyConfig;
import com.commons.proxy.center.discovery.IProxyDiscovery;
import com.commons.proxy.center.discovery.listener.ProxyNodeCacheListener;
import com.commons.proxy.center.route.IProxyRoute;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2015-2016 中盈优创
 * ZookeeperProxyDiscovery
 * Author: 龚健
 * Date: 2016/1/15
 */
public class ZookeeperProxyDiscovery implements IProxyDiscovery {

    final static Logger logger = LoggerFactory.getLogger(ZookeeperProxyDiscovery.class);

    private ZookeeperProxyConfig proxyConfig;
    private IProxyRoute proxyRoute;

    public void setProxyConfig(BaseProxyConfig proxyConfig) {
        this.proxyConfig = (ZookeeperProxyConfig) proxyConfig;
    }

    public void setProxyRoute(IProxyRoute proxyRoute) {
        this.proxyRoute = proxyRoute;
    }

    private PathChildrenCache cache = null;
    private PathChildrenCacheListener listener = null;

    @Override
    public void discovery() throws ServiceException {

        try {
            CuratorFramework client = proxyConfig.getClient();
            cache = new PathChildrenCache(client, proxyConfig.getProvidePath(), true);
            listener = new ProxyNodeCacheListener(proxyRoute, cache);
            cache.getListenable().addListener(listener);
            cache.start();
        } catch (Exception e) {
            CloseableUtils.closeQuietly(cache);
            throw new ServiceException(e);
        }
    }

    @Override
    public void destroy() throws ServiceException {
        if (cache != null) {
            try {
                cache.getListenable().removeListener(listener);
            } catch (Exception e) {

                throw new ServiceException(e);
            } finally {
                listener = null;
                try {
                    CloseableUtils.closeQuietly(cache);
                } catch (Exception e) {
                    throw new ServiceException(e);
                } finally {
                    cache = null;
                }
            }

        }
    }
}
