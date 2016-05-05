package com.verdant.jtools.common.factory.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.*;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * HttpClient工厂
 *
 * @author verdant
 * @version 2016.4.20
 */
public class HttpClientFacory {

    private static CloseableHttpClient httpClient = null;

    private static PoolingHttpClientConnectionManager httpClientConnectionManager = null;
    private static RequestConfig defaultRequestConfig = null;
    private static TrustManager[] trustManagers = new TrustManager[1];
    private static SSLContext sslContext = null;
    private static SSLConnectionSocketFactory sslSocketFactory = null;

    private final static int TIMEOUT_CONNECTION = 60 * 1000;
    private final static int TIMEOUT_SOCKET = 60 * 1000;
    private final static int MAX_TOTAL = 200;// 设置连接池线程最大数量
    private final static int MAX_RETRY = 5;
    private final static int MAX_ROUTE_TOTAL = 20; // 设置单个路由最大的连接线程数量
    private final static String PROTOCOL_SSL = "TLS";

    public static CloseableHttpClient getInstance() {
        return httpClient;
    }

    static {
        // 重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        initSSL();
        initConnectionManager();
        // 初始化httpclient客户端
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
        httpClientBuilder.setRedirectStrategy(redirectStrategy);
        httpClientBuilder.setRetryHandler(inintRetryHandler());

        httpClient = httpClientBuilder.build();
    }

    public static void destory() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HttpRequestRetryHandler inintRetryHandler() {
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= MAX_RETRY) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    return true;
                }
                return false;
            }
        };
        return retryHandler;
    }

    private static void initConnectionManager() {
        // Connection配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();
        // 默认请求配置
        defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT_SOCKET)
                .setConnectTimeout(TIMEOUT_CONNECTION)
                .setConnectionRequestTimeout(TIMEOUT_CONNECTION)
                .build();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();
        // 创建httpclient连接池
        httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        httpClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_TOTAL);
        httpClientConnectionManager.setDefaultConnectionConfig(connectionConfig);
    }

    private static void initSSL() {
        //覆盖证书检测过程（用以非CA的https链接）
        trustManagers[0] = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        try {
//            sslContext = SSLContexts.custom()
//                    .useProtocol(PROTOCOL_SSL)
//                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                    .build();
//            sslSocketFactory = new SSLConnectionSocketFactory(sslContext);


            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
            sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
