package com.verdant.jtools.proxy.center.route;

import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.ServiceInfo;

import java.util.List;

/**
 * 路由管理接口
 * Copyright (C), 2015-2016 中盈优创
 * IProxyRoute
 * Author: 龚健
 * Date: 2015/10/16
 */
public interface IProxyRoute {

    void remove(String path) throws ServiceException;

    void addOrUpdate(String path, String content) throws ServiceException;

    List<ServiceInfo> getServices(String domain, String interfaceName, String proxyType, float maxVersion) throws ServiceException;

    List<ServiceInfo> getServices(String domain, String interfaceName, float maxVersion) throws ServiceException;
}
