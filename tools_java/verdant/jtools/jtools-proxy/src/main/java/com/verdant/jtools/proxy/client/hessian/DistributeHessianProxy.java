package com.verdant.jtools.proxy.client.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;
import com.commons.log.utils.LoggerContextUtil;
import com.commons.proxy.center.model.RequestAuthorize;
import com.commons.proxy.center.model.RequestTrace;
import com.commons.proxy.center.model.ServiceInfo;
import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.center.secure.RequestAuthorizeUtil;
import com.commons.proxy.center.transfer.model.TransferType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;


public class DistributeHessianProxy extends HessianProxy {

    private static final Logger logger = LoggerFactory.getLogger(DistributeHessianProxy.class);

    private IProxyProvider proxyProvider;
    private String serviceInterfaceName;
    private float maxVersion;

    public void setProxyProvider(IProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    public void setServiceInterfaceName(String serviceInterfaceName) {
        this.serviceInterfaceName = serviceInterfaceName;
    }

    public void setMaxVersion(float maxVersion) {
        this.maxVersion = maxVersion;
    }

    protected DistributeHessianProxy(URL url, HessianProxyFactory factory, Class<?> type) {
        super(url, factory, type);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ServiceInfo service = null;
        if (proxyProvider != null) {
            service = proxyProvider.getProxyService(serviceInterfaceName, maxVersion, TransferType.HESSIAN.name());

        }
        if (service != null) {
            logger.debug("invoke service {}", service.toString());
            String urlStr = service.getPlatform().getUrl() + service.getName();
            URL url = new URL(urlStr);
            Field field = ReflectionUtils.findField(this.getClass(), "_url");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, this, url);
        }
        Object result = null;
        try {
            //super invoke 可传递参数InputStream 但是使用Hessian2协议时返回InputStream读取则会报stream is closed
            //因为invoke的final块 始终执行is.close()
            //服务器返回InputStream使用byte[]替代
            result = super.invoke(proxy, method, args);
        } catch (Throwable throwable) {
            throw throwable;
        }
        return result;
    }

    @Override
    protected void addRequestHeaders(HessianConnection conn) {
        super.addRequestHeaders(conn);
        //请求添加签名参数
        addRequestAuthHeaders(conn);
    }

    @Override
    protected void parseResponseHeaders(URLConnection conn) {
        super.parseResponseHeaders(conn);
    }

    private void addRequestAuthHeaders(HessianConnection conn) {
        RequestAuthorize auth = RequestAuthorizeUtil.generate();
        conn.addHeader(RequestAuthorize.PROXY_ID, auth.getId());
        conn.addHeader(RequestAuthorize.PROXY_SIGN, auth.getSignature());
        conn.addHeader(RequestAuthorize.PROXY_TIME, auth.getTimestamp());
        conn.addHeader(RequestTrace.PROXY_TRACE_ID, LoggerContextUtil.getTraceId());
        conn.addHeader(RequestTrace.PROXY_SEQUENCE_ID, LoggerContextUtil.getSequenceId() + LoggerContextUtil.getSequenceIdLocalAndIncrement());

    }

}
