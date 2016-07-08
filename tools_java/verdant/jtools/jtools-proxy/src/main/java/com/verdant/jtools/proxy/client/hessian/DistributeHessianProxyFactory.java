package com.verdant.jtools.proxy.client.hessian;

import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;
import com.commons.proxy.center.provide.IProxyProvider;

import java.lang.reflect.Proxy;
import java.net.URL;


public class DistributeHessianProxyFactory extends HessianProxyFactory {

    private IProxyProvider proxyProvider;
    private String serviceInterfaceName;
    private float maxVersion;

    public void setServiceInterfaceName(String serviceInterfaceName) {
        this.serviceInterfaceName = serviceInterfaceName;
    }

    public void setMaxVersion(float maxVersion) {
        this.maxVersion = maxVersion;
    }

    public void setProxyProvider(IProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    /**
     * 替换自定义 DistributeHessianProxy
     * <p/>
     * HessianClientInterceptor调用此方法顺序如下
     * afterPropertiesSet
     * prepare
     * createHessianProxy
     *
     * @return 返回自定义远程对象Hessian代理
     */
    @Override
    public Object create(Class<?> api, URL url, ClassLoader loader) {
        if (api == null) {
            throw new NullPointerException("api must not be null for HessianProxyFactory.create()");
        } else {
            DistributeHessianProxy handler = null;
            handler = new DistributeHessianProxy(url, this, api);
            handler.setProxyProvider(proxyProvider);
            handler.setServiceInterfaceName(serviceInterfaceName);
            handler.setMaxVersion(maxVersion);
            return Proxy.newProxyInstance(loader, new Class[]{api, HessianRemoteObject.class}, handler);
        }
    }

    @Override
    protected HessianConnectionFactory createHessianConnectionFactory() {
//        return new DistributeHessianURLConnectionFactory();
        String className= System.getProperty(DistributeHessianURLConnectionFactory.class.getName());
        HessianConnectionFactory factory = null;
        try {
            if (className != null) {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Class<?> cl = Class.forName(className, false, loader);
                factory = (DistributeHessianURLConnectionFactory) cl.newInstance();
                return factory;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new DistributeHessianURLConnectionFactory();
    }
}
