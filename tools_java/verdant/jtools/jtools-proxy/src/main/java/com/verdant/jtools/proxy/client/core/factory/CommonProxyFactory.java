package com.verdant.jtools.proxy.client.core.factory;

import com.commons.proxy.center.model.ProxyConfigure;
import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.client.interceptor.ICommonClientInterceptor;
import com.commons.proxy.client.interceptor.impl.AutoClientInterceptor;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Copyright (C), 2015-2016 中盈优创
 * CommonProxyFactory
 * Author: 龚健
 * Date: 2016/4/20
 */
public class CommonProxyFactory {

    public Class<?> serviceInterface;
    public ProxyConfigure configure;
    public ClassLoader loader;
    public IProxyProvider provider;

    public CommonProxyFactory(Class<?> serviceInterface, ProxyConfigure configure, IProxyProvider proxyProvider, ClassLoader loader) {
        this.serviceInterface = serviceInterface;
        this.configure = configure;
        this.loader = loader;
        this.provider = proxyProvider;
    }

    public ICommonClientInterceptor createInterceptor() {
        return new AutoClientInterceptor(configure, provider);
    }

    public Object proxy() {
        return new ProxyFactory(configure.getServiceInterface(), createInterceptor()).getProxy(loader);
    }
}
