package com.verdant.jtools.proxy.server.handler;

import com.commons.common.utils.ContextUtil;
import com.commons.log.utils.LoggerContextUtil;
import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.RequestTrace;
import com.commons.proxy.center.secure.RequestAuthorizeUtil;
import com.commons.proxy.center.transfer.model.TransferRequestMessage;
import com.commons.proxy.center.transfer.model.TransferResponseMessage;
import com.commons.proxy.serializable.Serializer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.server.ServletServerHttpAsyncRequestControl;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.remoting.support.RemoteInvocationUtils;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

/**
 * 远程服务http发布 异步非阻塞执行
 * Copyright (C), 2015-2016 中盈优创
 * RemoteServiceExporter
 * Author: 龚健
 * Date: 2016/4/22
 */
public class HttpServiceExporter extends RemoteInvocationBasedExporter implements InitializingBean, HttpRequestHandler {

    private static Logger logger = LoggerFactory.getLogger(HttpServiceExporter.class);


    public HttpServiceExporter() {

    }

    private String executorName = "taskExecutor";


    public AsyncTaskExecutor getExecutor() {
        return ContextUtil.getBean(this.executorName, AsyncTaskExecutor.class);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        logger.debug("Service handleRequest ");
        final ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        final ServletServerHttpRequest httpRequest = new ServletServerHttpRequest(request);
        final ServletServerHttpAsyncRequestControl asyncControl = new ServletServerHttpAsyncRequestControl(httpRequest, httpResponse);

        final ListenableFutureTask<TransferResponseMessage> future = new ListenableFutureTask(new Callable<TransferResponseMessage>() {
            @Override
            public TransferResponseMessage call() throws Exception {
                try {
                    LoggerContextUtil.reset();
                    LoggerContextUtil.setTraceId(httpRequest.getServletRequest().getHeader(RequestTrace.PROXY_TRACE_ID));
                    LoggerContextUtil.setSequenceId(httpRequest.getServletRequest().getHeader(RequestTrace.PROXY_SEQUENCE_ID));
                    RequestAuthorizeUtil.valid(httpRequest.getServletRequest());
                    byte[] inputBytes = IOUtils.toByteArray(httpRequest.getServletRequest().getInputStream());
                    TransferRequestMessage requestMessage = Serializer.deserialize(inputBytes);
                    RemoteInvocation invocation = new RemoteInvocation(requestMessage.getMethod(), requestMessage.getParameterTypes(), requestMessage.getParameters());

                    RemoteInvocationResult result = invokeAndCreateResult(invocation, getProxyForService());
                    TransferResponseMessage responseMessage = new TransferResponseMessage();
                    responseMessage.setMessageId(requestMessage.getMessageId());
                    responseMessage.setTraceId(requestMessage.getTraceId());
                    responseMessage.setResult(result.getValue());
                    responseMessage.setException(convertException(result.getException()));
                    return responseMessage;
                } catch (Exception e) {
                    logger.error("ListenableFutureTask call throw Exception {}", e);
                    throw e;
                }

            }
        });

        future.addCallback(new ListenableFutureCallback<TransferResponseMessage>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.debug("ListenableFutureCallback onFailure {}", throwable);
                TransferResponseMessage error = new TransferResponseMessage();
                error.setException(convertException(throwable));
                onSuccess(error);
            }

            @Override
            public void onSuccess(TransferResponseMessage responseMessage) {
                try {
                    logger.debug("ListenableFutureCallback onSuccess {}", responseMessage);
                    IOUtils.write(Serializer.serialize(responseMessage), httpResponse.getServletResponse().getOutputStream());
                } catch (IOException e) {
                    logger.error("ListenableFutureCallback onSuccess-IOException {}", e);
                } finally {
                    asyncControl.complete();
                }

            }
        });

        asyncControl.start();
        ((AsyncListenableTaskExecutor) getExecutor()).submitListenable(future);


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.checkService();
        this.checkServiceInterface();
        this.setInterceptors(new Object[]{new AsyncAnnotationAdvisor(getExecutor(), new SimpleAsyncUncaughtExceptionHandler())});
    }

    /**
     * 判断异常并包装
     *
     * @param exception
     * @return
     */
    public ServiceException convertException(Throwable exception) {
        if (exception != null) {
            Throwable exToThrow = exception;
            if (exception instanceof InvocationTargetException) {
                exToThrow = ((InvocationTargetException) exception).getTargetException();
            }
            RemoteInvocationUtils.fillInClientStackTraceIfPossible(exToThrow);
            if (exToThrow instanceof CancellationException) {
                return new ServiceException(ResultCode.OPERATE_TIMEOUT);
            }
            return new ServiceException(exToThrow);
        }
        return null;
    }


}
