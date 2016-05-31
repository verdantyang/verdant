
package com.verdant.jtools.common.web.utils.http;

import com.verdant.jtools.common.web.factory.HttpClientFacory;
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
import java.util.Map.Entry;

/**
 * @author verdant
 * @create 2016/4/20
 * @Desc: HTTP请求
 */
public class HttpClientUtils2 {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils2.class);

    private static CloseableHttpClient httpClient = HttpClientFacory.getInstance();
    private static HttpClientContext httpClientContext = null;
    private static CookieStore cookieStore = null;

    static {
        /* 默认cookie */
        cookieStore = new BasicCookieStore();

        // httpclient上下文
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
    }

    public static InputStream postStream(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
        return post(url, entity, headers).getContent();
    }

    public static String postPlain(String url, HttpEntity entity, Map<String, String> headers) throws Exception {
        return EntityUtils.toString(post(url, entity, headers));
    }

    public static HttpEntity post(String url, HttpEntity entity, Map<String, String> headers) {
        logger.debug("Post -> " + url);
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        handleHeader(post, headers);
        try {
            response = httpClient.execute(post, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException();
            }
            return response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpEntity get(String url, Map<String, Object> params, Map<String, String> headers) {
        logger.debug("Get -> " + url);
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(UrlUtils2.handleURL(url, params));
        handleHeader(get, headers);
        try {
            response = httpClient.execute(get, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("Http client error response status " + statusCode);
            }
            return response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void release(HttpEntity httpEntity) {
        try {
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
                httpEntity.consumeContent();
//                    response.getEntity().consumeContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleHeader(HttpRequestBase entity, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> entry : headers.entrySet()) {
            entity.addHeader(entry.getKey(), entry.getValue());
        }
    }

}
