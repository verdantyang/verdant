package com.verdant.jtools.proxy.center.policy.impl;



import com.verdant.jtools.proxy.center.model.ServiceInfo;
import com.verdant.jtools.proxy.center.policy.ILoadPolicy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Copyright (C), 2015-2016 中盈优创
 * BaseLoadPolicy
 * Author: 龚健
 * Date: 2016/2/3
 */
public abstract class AbstractLoadPolicy implements ILoadPolicy {
    @Override
    public URL getUrl(List<ServiceInfo> services) {
        ServiceInfo service = analysis(services);
        String urlString = service.getPlatform().getUrl() + service.getName();
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
