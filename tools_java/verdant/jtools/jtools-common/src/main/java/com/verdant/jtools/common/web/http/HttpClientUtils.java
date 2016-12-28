
package com.verdant.jtools.common.web.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * HTTP请求
 *
 * @author verdant
 * @version 2016.4.20
 */
public class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static CloseableHttpClient httpClient = HttpClientFactory.getHttpClient();
    private static HttpClientContext httpClientContext = null;

    static {
        /* 默认cookie */
        CookieStore cookieStore = new BasicCookieStore();

        // httpclient上下文
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
    }

    public static String getPlain(String url, Map<String, Object> params, Map<String, String> headers) {
        HttpEntity httpEntity = get(url, params, headers);
        return handleHttpEntity(httpEntity);

    }

    public static String postPlain(String url, HttpEntity entity, Map<String, String> headers) {
        HttpEntity httpEntity = post(url, entity, headers);
        return handleHttpEntity(httpEntity);
    }

    public static InputStream postStream(String url, HttpEntity entity, Map<String, String> headers) {
        HttpEntity httpEntity = post(url, entity, headers);
        try {
            if (null != httpEntity)
                return httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpEntity get(String url, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(UrlUtils.handleURL(url, params));
        handleHeader(get, headers);
        try {
            long start = System.currentTimeMillis();
            response = httpClient.execute(get, httpClientContext);
            long end = System.currentTimeMillis();
            logger.info("http get {} used {}ms", get.getURI().toString(), end - start);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("http get status is not 200, url={}", url);
            }
            return response.getEntity();
        } catch (IOException e) {
            logger.error("http get error, url={}", url);
        }
        return null;
    }

    public static HttpEntity post(String url, HttpEntity entity, Map<String, String> headers) {
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        handleHeader(post, headers);
        try {
            long start = System.currentTimeMillis();
            response = httpClient.execute(post, httpClientContext);
            long end = System.currentTimeMillis();
            logger.info("http post {} used {}ms", url, end - start);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("http post status is not 200, url={}", url);
            }
            return response.getEntity();
        } catch (IOException e) {
            logger.error("http post error, url={}", url);
        }
        return null;
    }


    public static void release(HttpEntity httpEntity) {
        try {
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException e) {
            logger.error("http release entity error");
        }
    }

    private static void handleHeader(HttpRequestBase entity, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            entity.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 处理HttpEntity
     *
     * @param httpEntity
     * @return
     */
    private static String handleHttpEntity(HttpEntity httpEntity) {
        try {
            if (null != httpEntity)
                return EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(httpEntity);
        }
        return null;
    }
}
