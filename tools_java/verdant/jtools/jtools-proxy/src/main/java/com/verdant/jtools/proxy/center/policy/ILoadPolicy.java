package com.verdant.jtools.proxy.center.policy;

import com.commons.proxy.center.model.ServiceInfo;

import java.net.URL;
import java.util.List;

/**
 * 负载接口
 * Copyright (C), 2015-2016 中盈优创
 * ILoadPolicy
 * Author: 龚健
 * Date: 2015/10/16
 */
public interface ILoadPolicy {

    /**
     * 按策略解析一组服务获取 Service 的URL
     * @param services
     * @return
     */
    URL getUrl(List<ServiceInfo> services);

    /**
     * 按策略解析一组服务获取 Service
     * @param services
     * @return
     */
    ServiceInfo analysis(List<ServiceInfo> services);
}
