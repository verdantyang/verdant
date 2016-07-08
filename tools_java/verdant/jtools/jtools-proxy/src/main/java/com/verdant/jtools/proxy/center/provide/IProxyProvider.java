package com.verdant.jtools.proxy.center.provide;

import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.ServiceInfo;
import com.commons.proxy.center.policy.ILoadPolicy;
import com.commons.proxy.center.route.IProxyRoute;

import java.net.URL;

/**
 * Copyright (C), 2015-2016 中盈优创
 * IProxyProvider
 * Author: 龚健
 * Date: 2016/1/15
 */
public interface IProxyProvider {

    void setLoadPolicy(ILoadPolicy loadPolicy);

    void setProxyRoute(IProxyRoute proxyRoute);

    ILoadPolicy getLoadPolicy();

    IProxyRoute getProxyRoute();

    ServiceInfo getProxyService(String interfaceName, float maxVersion, String proxyType) throws ServiceException;

    URL getProxyServiceURL(String interfaceName, float maxVersion, String proxyType) throws ServiceException;

    ServiceInfo getProxyService(String interfaceName, float maxVersion) throws ServiceException;

    URL getProxyServiceURL(String interfaceName, float maxVersion) throws ServiceException;

}
