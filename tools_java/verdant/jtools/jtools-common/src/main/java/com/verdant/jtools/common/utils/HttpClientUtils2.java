
package com.verdant.jtools.common.utils;

import com.alibaba.fastjson.JSON;
import com.verdant.jtools.common.factory.HttpClientFacory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * HTTP请求
 *
 * @author verdant
 * @version 2016.4.20
 */
public class HttpClientUtils2 {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils2.class);

    private static CloseableHttpClient httpClient = HttpClientFacory.getInstance();
    private static HttpClientContext httpClientContext = null;
    private static CookieStore cookieStore = null;

    private static void initHttpClientContext() {
        /* 默认cookie */
        cookieStore = new BasicCookieStore();

        // httpclient上下文
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
    }

    private static MultipartEntityBuilder processBuilderParams(Map<String, Object> params) {

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

    public static InputStream postStream(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return post(url, params, headers).getContent();
    }

    public static String postPlain(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        return EntityUtils.toString(post(url, params, headers));
    }

    public static String postPlain(String url, HttpEntity entity, Map<String, String> headers) {
        String result = null;
        try {
            result = EntityUtils.toString(post(url, entity, headers));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HttpEntity post(String url, HttpEntity entity, Map<String, String> headers) {
        logger.debug("Post -> " + url);
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        handleHeader(post, headers);
        return doPost(post);
    }

    public static HttpEntity post(String url, Map<String, Object> params, Map<String, String> headers) {
        logger.debug("Post -> " + url);
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = processBuilderParams(params);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Consts.UTF_8);
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        handleHeader(post, headers);
        return doPost(post);
    }

    private static HttpEntity doPost(HttpPost post) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException();
            }
            return response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpEntity get(String url, Map<String, Object> params, Map<String, String> headers) {
        logger.debug("Get -> " + url);

        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(handleURL(url, params));
            handleHeader(get, headers);
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

    private static void handleHeader(HttpRequestBase entity, Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        for (Entry<String, String> entry : headers.entrySet()) {
            entity.addHeader(entry.getKey(), entry.getValue());
        }
    }

    private static String handleURL(String processUrl, Map<String, Object> params) {
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

    private static void release(HttpEntity httpEntity) {
        try {
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
//                    response.getEntity().consumeContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
