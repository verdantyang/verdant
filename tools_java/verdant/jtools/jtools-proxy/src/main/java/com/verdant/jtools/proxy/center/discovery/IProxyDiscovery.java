package com.verdant.jtools.proxy.center.discovery;


import com.verdant.jtools.proxy.center.config.BaseProxyConfig;
import com.verdant.jtools.proxy.center.route.IProxyRoute;

public interface IProxyDiscovery {

    /**
     * 基础配置
     *
     * @param config
     */
    void setProxyConfig(BaseProxyConfig config);

    /**
     * @param proxyRoute
     */
    void setProxyRoute(IProxyRoute proxyRoute);

    /**
     * 监听服务 更新 IProxyRoute
     *
     * @throws ServiceException
     */
    void discovery() throws ServiceException;

    void destroy() throws ServiceException;
}
