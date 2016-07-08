package com.verdant.jtools.proxy.client.core.impl;


import com.verdant.jtools.proxy.center.model.ServiceInfo;
import com.verdant.jtools.proxy.center.provide.IProxyProvider;
import com.verdant.jtools.proxy.center.model.ProxyConfigure;
import com.verdant.jtools.proxy.center.policy.ILoadPolicy;
import com.verdant.jtools.proxy.center.route.IProxyRoute;
import com.verdant.jtools.proxy.center.transfer.IProxyTransfer;
import com.verdant.jtools.proxy.center.transfer.factory.ProxyTransferFactory;
import com.verdant.jtools.proxy.center.transfer.model.TransferType;
import com.verdant.jtools.proxy.client.core.ICommonProxy;
import org.springframework.core.task.AsyncTaskExecutor;


public class CommonProxy implements ICommonProxy {

    public ProxyConfigure configure;
    public IProxyProvider provider;
    private AsyncTaskExecutor executor;
    private String executorName = "taskExecutor";

    public CommonProxy(ProxyConfigure configure, IProxyProvider provider) {
        this.configure = configure;
        this.provider = provider;
    }



    @Override
    public ProxyConfigure getConfigure() {
        return configure;
    }

    @Override
    public IProxyRoute getRouter() {
        return provider.getProxyRoute();
    }

    @Override
    public ILoadPolicy getPolicy() {
        return provider.getLoadPolicy();
    }

    @Override
    public IProxyProvider getProvider() {
        return provider;
    }

    @Override
    public IProxyTransfer getTransfer(TransferType transferType) throws ServiceException {
        return ProxyTransferFactory.create(transferType);
    }

    @Override
    public ServiceInfo getService() throws ServiceException {
        return this.getProvider().getProxyService(this.getConfigure().getServiceInterfaceName(), this.getConfigure().getMaxVersion());
    }

    @Override
    public AsyncTaskExecutor getExecutor() {
        if (this.executor != null) {
            return this.executor;
        } else if (this.executorName != null) {
            return ContextUtil.getBean(this.executorName, AsyncTaskExecutor.class);
        } else {
            return null;
        }
    }

    @Override
    public Object transferRequest(TransferRequestMessage message) throws ServiceException {
        IProxyTransfer transfer = getTransfer(TransferType.getEnum(message.getTransferType()));
        return transfer.send(message).getResult();
    }

}
