package com.verdant.jtools.common.web.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
public class HttpClientFactory {

    private static CloseableHttpClient httpClient = null;

    private static PoolingHttpClientConnectionManager httpClientConnectionManager = null;
    private static RequestConfig defaultRequestConfig = null;
    private static SSLConnectionSocketFactory sslSocketFactory = null;

    private final static int MAX_TOTAL = 300;                // 设置连接池最大数量
    private final static int MAX_ROUTE_TOTAL = 200;          // 设置单个路由最大的连接线程数量
    private final static int TIMEOUT_CONNECTION = 60 * 1000;
    private final static int TIMEOUT_SOCKET = 60 * 1000;
    private final static int MAX_RETRY = 5;
    private final static String PROTOCOL_SSL = "TLS";

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    static {
        initSSL();
        initConnectionManager();
        // 初始化httpclient客户端
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
//        httpClientBuilder.setRetryHandler(initRetryHandler());
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        httpClient = httpClientBuilder.build();
    }

    /**
     * 关闭客户端
     */
    public static void destroy() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化连接管理
     */
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

    /**
     * 初始化SSL
     */
    private static void initSSL() {
        TrustManager[] trustManagers = new TrustManager[1];
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
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_SSL);
            sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
            sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化重试策略
     *
     * @return
     */
    private static HttpRequestRetryHandler initRetryHandler() {
        return new HttpRequestRetryHandler() {
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
                HttpRequest request = (HttpRequest) context.getAttribute("http.request");
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };
    }
}
