package com.verdant.jtools.proxy.center.registy;

import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.ServiceInfo;

import java.util.List;

/**
 * 代理注册接口
 * 提供发布和订阅
 * Copyright (C), 2015-2016 中盈优创
 * IZookeeperProxyConfig
 * Author: 龚健
 * Date: 2015/10/13
 */
public interface IProxyRegistry {

    /**
     * 服务注册
     */
    void register(List<ServiceInfo> services) throws ServiceException;

}
