package com.verdant.jtools.proxy.center.policy.impl;

import com.commons.proxy.center.model.ServiceInfo;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮询
 * Copyright (C), 2015-2016 中盈优创
 * RoundRobinLoadPolicy
 * Author: 龚健
 * Date: 2015/10/16
 */
public class RoundRobinLoadPolicy extends AbstractLoadPolicy {

    private Map<String, AtomicLong> indexMap = Maps.newConcurrentMap();

    @Override
    public ServiceInfo analysis(List<ServiceInfo> services) {
        String key = services.get(0).getPlatform().getDomain() + "_" + services.get(0).getInterfaceName();
        AtomicLong atomicLong = indexMap.get(key);
        if (atomicLong == null) {
            atomicLong = new AtomicLong(0);
            indexMap.put(key, atomicLong);
        }
        int index = (int) (atomicLong.getAndAdd(1) % services.size());
        return services.get(Math.abs(index));
    }
}
