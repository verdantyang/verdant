
package com.jtools.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CodingErrorAction;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 访问API帮助
 *
 * @author gj
 * @version 2015年5月7日
 */
public class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static String userAgent;

    private final static int TIMEOUT_CONNECTION = 60 * 1000;

    private final static int TIMEOUT_SOCKET = 60 * 1000;

    private final static int MAX_TOTAL = 200;

    private final static int MAX_RETRY = 5;

    private final static int MAX_ROUTE_TOTAL = 20;

    private CloseableHttpClient httpClient = null;

    private HttpClientContext httpClientContext = null;

    private CookieStore cookieStore = null;

    private PoolingHttpClientConnectionManager httpClientConnectionManager = null;

    private static HttpClientUtils helper;

    RequestConfig defaultRequestConfig = null;

    TrustManager[] trustManagers = new TrustManager[1];
    SSLContext sslContext = null;
    SSLConnectionSocketFactory sslSocketFactory = null;

    public static void destory() {
        try {
            helper.httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpClientUtils getInstance() {
        if (helper == null) {
            helper = new HttpClientUtils();
            helper.initialize();
        }
        return helper;
    }

    private HttpClientUtils() {
    }

    private void initialize() {
        // Connection配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).build();

        // 设置重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        // 重试策略
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= MAX_RETRY) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // Do not retry on SSL handshake exception
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };

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
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
            sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 默认请求配置
        defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT_SOCKET)
                .setConnectTimeout(TIMEOUT_CONNECTION)
                .setConnectionRequestTimeout(TIMEOUT_CONNECTION).build();

        // 创建httpclient连接池
        httpClientConnectionManager = new PoolingHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create().
                        register("http", PlainConnectionSocketFactory.getSocketFactory()).
                        register("https",sslSocketFactory).build());
        httpClientConnectionManager.setMaxTotal(MAX_TOTAL); // 设置连接池线程最大数量
        httpClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_TOTAL); // 设置单个路由最大的连接线程数量
        httpClientConnectionManager.setDefaultConnectionConfig(connectionConfig);

        /* 默认cookie */
        cookieStore = new BasicCookieStore();

        // httpclient上下文
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);

        // 初始化httpclient客户端
        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRedirectStrategy(redirectStrategy)
                .setRetryHandler(retryHandler);

        if (this.userAgent != null) {
            httpClientBuilder.setUserAgent(userAgent);
        }
        httpClient = httpClientBuilder.build();

    }

    private MultipartEntityBuilder processBuilderParams(Map<String, Object> params) {

        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (params != null) {
            logger.debug(params.toString());
            Set<Entry<String, Object>> sets = params.entrySet();
            for (Entry<String, Object> entry : sets) {
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                } else if (value instanceof File) {
                    builder.addBinaryBody(entry.getKey(), (File) value);
                } else if (value instanceof CharSequence) {
                    builder.addTextBody(entry.getKey(), value.toString(), contentType);
                } else {
                    builder.addTextBody(entry.getKey(), JSON.toJSONString(value), contentType);
                }
            }
        }
        return builder;
    }

    public InputStream postStream(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return this.post(url, params, headers).getContent();
    }

    public String postPlain(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return EntityUtils.toString(this.post(url, params, headers));
    }

    public String postPlain(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
        return EntityUtils.toString(this.post(url, entity, headers));
    }

    public HttpEntity post(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        logger.debug(url);
        post.setEntity(entity);
        processHeader(post, headers);
        return doPost(post);
    }

    public HttpEntity post(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        logger.debug(url);
        MultipartEntityBuilder builder = processBuilderParams(params);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Consts.UTF_8);
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        processHeader(post, headers);
        return doPost(post);
    }

    private HttpEntity doPost(HttpPost post) throws Exception {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException();
            }
            return response.getEntity();
        } catch (Exception e) {
            throw e;
        } finally {
            release(response);
        }
    }

    public InputStream getStream(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return this.get(url, params, headers).getContent();
    }

    public String getPlain(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return EntityUtils.toString(this.get(url, params, headers));
    }

    public HttpEntity get(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        CloseableHttpResponse response = null;
        try {
            logger.debug(url);
            HttpGet get = new HttpGet(processURL(url, params));
            processHeader(get, headers);
            response = httpClient.execute(get, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("http client error response status "+statusCode);
            }
            return response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            release(response);
        }
    }

    private void processHeader(HttpRequestBase entity, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> entry : headers.entrySet()) {
            entity.addHeader(entry.getKey(), entry.getValue());
        }
    }

    private String processURL(String processUrl, Map<String, Object> params) {
        if (params == null) {
            return processUrl;
        }
        logger.debug(params.toString());
        StringBuilder url = new StringBuilder(processUrl);
        if (url.indexOf("?") < 0)
            url.append('?');

        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
        }
        return url.toString().replace("?&", "?");
    }

    private void release(CloseableHttpResponse response) {
        try {
            if (response != null) {
                if (response.getEntity() != null) {
                    response.getEntity().consumeContent();
                }
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
//        System.out.println(HttpClientUtils.getInstance().getPlain("http://192.168.6.221:8080/cgpboss-admin-web/api/engine/health/192.168.6.123",null,null));
        System.out.println(HttpClientUtils.getInstance().postPlain("https://open.weixin.qq.com/",
                new StringEntity("{}", ContentType.APPLICATION_JSON),null));
        System.out.println(HttpClientUtils.getInstance().postPlain("https://kyfw.12306.cn/otn/leftTicket/init",
                new StringEntity("{}", ContentType.APPLICATION_JSON),null));
    }
}
