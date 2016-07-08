package com.verdant.jtools.proxy.client.interceptor.impl;

import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.ProxyConfigure;
import com.commons.proxy.center.model.ServiceInfo;
import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.center.transfer.factory.ProxyTransferFactory;
import com.commons.proxy.center.transfer.model.TransferRequestMessage;
import com.commons.proxy.center.transfer.model.TransferType;
import com.commons.proxy.client.core.impl.CommonProxy;
import com.commons.proxy.client.interceptor.ICommonClientInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * AbstractCommonClientInterceptor
 * Author: 龚健
 * Date: 2016/4/20
 */
public abstract class CommonClientInterceptor extends CommonProxy implements ICommonClientInterceptor {

    private static Logger logger = LoggerFactory.getLogger(CommonClientInterceptor.class);

    public CommonClientInterceptor(ProxyConfigure configure, IProxyProvider provider) {
        super(configure, provider);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object specialObj = invokeSpecial(invocation);
        if (specialObj != null) {
            return specialObj;
        }
        final ServiceInfo service = this.getService();
        final TransferType transferType = TransferType.getEnum(service.getTransfer());
        final TransferRequestMessage message = ProxyTransferFactory.buildRequestMessage(service, invocation);
        if (getExecutor() == null) {
            //无线程执行器
            return transferRequest(message);
        }
        if (transferType == TransferType.HTTP) {
            //有线程执行器
            return invokeSyncNonBlocking(invocation, service, transferType, message);
        }

        throw new ServiceException(ResultCode.ERROR_INNER);
    }

    public Object invokeSpecial(MethodInvocation invocation) {
        // equals and hashCode and toString are special cased
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (methodName.equals("equals") && parameterTypes.length == 1 && parameterTypes[0].equals(Object.class)) {
            return true;
        } else if (methodName.equals("hashCode") && arguments.length == 0) {
            return new Integer(this.hashCode());
        } else if (methodName.equals("toString") && arguments.length == 0) {
            return this.toString();
        }
        return null;
    }

    public Object invokeSyncNonBlocking(final MethodInvocation invocation, final ServiceInfo service, final TransferType type, final TransferRequestMessage message) throws Throwable {
        //子线程包装  线程阻塞get()
        Callable<Object> task = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
//                    TransferRequestMessage message = ProxyTransferFactory.buildRequestMessage(service, invocation);
                    return transferRequest(message);
                } catch (Throwable ex) {
                    return new ServiceException(ex);
                }
            }
        };
        ListenableFutureTask<Object> future = new ListenableFutureTask(task);
        ((AsyncListenableTaskExecutor) getExecutor()).submitListenable(future);
        Object obj = future.get();
        if (obj instanceof ServiceException) {
            throw (ServiceException) obj;
        }
        return obj;

    }


}
