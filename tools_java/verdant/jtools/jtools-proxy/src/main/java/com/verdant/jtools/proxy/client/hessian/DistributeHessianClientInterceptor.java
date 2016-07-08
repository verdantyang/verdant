package com.verdant.jtools.proxy.client.hessian;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.caucho.hessian.io.SerializerFactory;
import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.net.MalformedURLException;


public class DistributeHessianClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor {
    DistributeHessianProxyFactory proxyFactory = new DistributeHessianProxyFactory();
    private Object hessianProxy;

    public DistributeHessianClientInterceptor() {
    }

    public void setProxyFactory(DistributeHessianProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory != null ? proxyFactory : new DistributeHessianProxyFactory();
    }

    public void setSerializerFactory(SerializerFactory serializerFactory) {
        this.proxyFactory.setSerializerFactory(serializerFactory);
    }

    public void setSendCollectionType(boolean sendCollectionType) {
        this.proxyFactory.getSerializerFactory().setSendCollectionType(sendCollectionType);
    }

    public void setAllowNonSerializable(boolean allowNonSerializable) {
        this.proxyFactory.getSerializerFactory().setAllowNonSerializable(allowNonSerializable);
    }

    public void setOverloadEnabled(boolean overloadEnabled) {
        this.proxyFactory.setOverloadEnabled(overloadEnabled);
    }

    public void setUsername(String username) {
        this.proxyFactory.setUser(username);
    }

    public void setPassword(String password) {
        this.proxyFactory.setPassword(password);
    }

    public void setDebug(boolean debug) {
        this.proxyFactory.setDebug(debug);
    }

    public void setChunkedPost(boolean chunkedPost) {
        this.proxyFactory.setChunkedPost(chunkedPost);
    }

    public void setConnectionFactory(HessianConnectionFactory connectionFactory) {
        this.proxyFactory.setConnectionFactory(connectionFactory);
    }

    public void setConnectTimeout(long timeout) {
        this.proxyFactory.setConnectTimeout(timeout);
    }

    public void setReadTimeout(long timeout) {
        this.proxyFactory.setReadTimeout(timeout);
    }

    public void setHessian2(boolean hessian2) {
        this.proxyFactory.setHessian2Request(hessian2);
        this.proxyFactory.setHessian2Reply(hessian2);
    }

    public void setHessian2Request(boolean hessian2) {
        this.proxyFactory.setHessian2Request(hessian2);
    }

    public void setHessian2Reply(boolean hessian2) {
        this.proxyFactory.setHessian2Reply(hessian2);
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.prepare();
    }

    public void prepare() throws RemoteLookupFailureException {
        try {
            this.hessianProxy = this.createHessianProxy(this.proxyFactory);
        } catch (MalformedURLException var2) {
            throw new RemoteLookupFailureException("Service URL [" + this.getServiceUrl() + "] is invalid", var2);
        }
    }

    protected Object createHessianProxy(DistributeHessianProxyFactory proxyFactory) throws MalformedURLException {
        Assert.notNull(this.getServiceInterface(), "\'serviceInterface\' is required");
        return proxyFactory.create(this.getServiceInterface(), this.getServiceUrl(), this.getBeanClassLoader());
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.hessianProxy == null) {
            throw new IllegalStateException("HessianClientInterceptor is not properly initialized - invoke \'prepare\' before attempting any operations");
        } else {
            ClassLoader originalClassLoader = this.overrideThreadContextClassLoader();

            Object ex;
            try {
                ex = invocation.getMethod().invoke(this.hessianProxy, invocation.getArguments());
            } catch (InvocationTargetException var10) {
                Throwable targetEx = var10.getTargetException();
                if (targetEx instanceof InvocationTargetException) {
                    targetEx = ((InvocationTargetException) targetEx).getTargetException();
                }

                if (targetEx instanceof HessianConnectionException) {
                    throw this.convertHessianAccessException(targetEx);
                }

                if (!(targetEx instanceof HessianException) && !(targetEx instanceof HessianRuntimeException)) {
                    if (targetEx instanceof UndeclaredThrowableException) {
                        UndeclaredThrowableException utex1 = (UndeclaredThrowableException) targetEx;
                        throw this.convertHessianAccessException(utex1.getUndeclaredThrowable());
                    }

                    throw targetEx;
                }

                Throwable utex = targetEx.getCause();
                throw this.convertHessianAccessException(utex != null ? utex : targetEx);
            } catch (Throwable var11) {
                throw new RemoteProxyFailureException("Failed to invoke Hessian proxy for remote service [" + this.getServiceUrl() + "]", var11);
            } finally {
                this.resetThreadContextClassLoader(originalClassLoader);
            }

            return ex;
        }
    }

    protected ServiceException convertHessianAccessException(Throwable ex) {
        if (!(ex instanceof HessianConnectionException) && !(ex instanceof ConnectException)) {
            //远程服务未知异常
            return new ServiceException(ResultCode.REMOTE_INVOKE_UNKNOWN_ERROR,ex);
        } else {
            //远程服务超时或不可达
            return new ServiceException(ResultCode.REMOTE_INVOKE_CONNECT_ERROR,ex);
        }
        //注释默认方法
        //return (RemoteAccessException) (!(ex instanceof HessianConnectionException) && !(ex instanceof ConnectException) ? new RemoteAccessException("Cannot access Hessian remote service at [" + this.getServiceUrl() + "]", ex) : new RemoteConnectFailureException("Cannot connect to Hessian remote service at [" + this.getServiceUrl() + "]", ex));
    }
}