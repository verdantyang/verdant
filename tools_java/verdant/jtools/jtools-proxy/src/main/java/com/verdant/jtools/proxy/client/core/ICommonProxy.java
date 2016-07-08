package com.verdant.jtools.proxy.client.core;


import org.springframework.core.task.AsyncTaskExecutor;


public interface ICommonProxy {

    ProxyConfigure getConfigure();

    AsyncTaskExecutor getExecutor();

    IProxyRoute getRouter();

    ILoadPolicy getPolicy();

    IProxyProvider getProvider();

    ServiceInfo getService() throws ServiceException;

    IProxyTransfer getTransfer(TransferType transferType) throws ServiceException;

    Object transferRequest(TransferRequestMessage message) throws ServiceException;
}
