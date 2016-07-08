package com.verdant.jtools.proxy.client.hessian;

import com.commons.proxy.center.provide.IProxyProvider;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;


public class DistributeHessianProxyFactoryBean extends DistributeHessianClientInterceptor implements FactoryBean<Object> {

    public void setServiceInterfaceName(String serviceInterfaceName) {
        this.proxyFactory.setServiceInterfaceName(serviceInterfaceName) ;
    }

    public void setMaxVersion(float maxVersion) {
        this.proxyFactory.setMaxVersion( maxVersion) ;
    }


    public void setProxyProvider(IProxyProvider proxyProvider) {
        this.proxyFactory.setProxyProvider(proxyProvider);
    }

    private Object serviceProxy;

    public DistributeHessianProxyFactoryBean() {
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = (new ProxyFactory(this.getServiceInterface(), this)).getProxy(this.getBeanClassLoader());
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return this.getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }
}
