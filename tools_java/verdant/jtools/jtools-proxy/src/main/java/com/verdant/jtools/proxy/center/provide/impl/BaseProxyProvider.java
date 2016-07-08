package com.verdant.jtools.proxy.center.provide.impl;

import com.commons.common.utils.PropUtil;
import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.ServiceInfo;
import com.commons.proxy.center.policy.ILoadPolicy;
import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.center.route.IProxyRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * Copyright (C), 2015-2016 中盈优创
 * BaseProxyProvider
 * Author: 龚健
 * Date: 2016/1/15
 */
public class BaseProxyProvider implements IProxyProvider {

    final static Logger logger = LoggerFactory.getLogger(BaseProxyProvider.class);

    private ILoadPolicy loadPolicy;
    private IProxyRoute proxyRoute;
    private String domain;

    public void setDomain(String domain) {
        if (domain.startsWith("$")) {
            domain = PropUtil.getPlaceholder(domain, null, null);
        }
        this.domain = domain;
    }

    @Override
    public ILoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    @Override
    public IProxyRoute getProxyRoute() {
        return proxyRoute;
    }

    public void setLoadPolicy(ILoadPolicy loadPolicy) {
        this.loadPolicy = loadPolicy;
    }

    public void setProxyRoute(IProxyRoute proxyRoute) {
        this.proxyRoute = proxyRoute;
    }

    public ServiceInfo getProxyService(String interfaceName, float maxVersion, String proxyType) throws ServiceException {
        return (ServiceInfo) getProxyService(interfaceName, maxVersion, proxyType, false);
    }

    public URL getProxyServiceURL(String interfaceName, float maxVersion, String proxyType) throws ServiceException {
        return (URL) getProxyService(interfaceName, maxVersion, proxyType, true);
    }

    @Override
    public ServiceInfo getProxyService(String interfaceName, float maxVersion) throws ServiceException {
        return (ServiceInfo) getProxyService(interfaceName, maxVersion, null, false);
    }

    @Override
    public URL getProxyServiceURL(String interfaceName, float maxVersion) throws ServiceException {
        return (URL) getProxyService(interfaceName, maxVersion, null, true);
    }

    private Object getProxyService(String interfaceName, float maxVersion, String proxyType, boolean isUrl) throws ServiceException {
        //按 域 接口名 版本号 版本类型 过滤可用服务列表
        List<ServiceInfo> services = proxyRoute.getServices(domain, interfaceName, proxyType, maxVersion);
        if (services == null || services.isEmpty()) {
            logger.debug("cannot find service,domain {} interface {}  maxVersion {} proxyType {}", domain, interfaceName, maxVersion, proxyType);
            throw new ServiceException(ResultCode.REMOTE_INVOKE_CONNECT_ERROR);
        }
        logger.debug("find service,domain {} interface {}  maxVersion {} proxyType {}", domain, interfaceName, maxVersion, proxyType);

        //isUrl按需 按策略返回
        if (isUrl) {
            return loadPolicy.getUrl(services);
        }
        return loadPolicy.analysis(services);
    }

}
