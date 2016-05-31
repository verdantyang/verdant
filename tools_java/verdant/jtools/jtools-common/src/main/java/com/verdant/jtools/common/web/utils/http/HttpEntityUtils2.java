package com.verdant.jtools.common.web.utils.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @author verdant
 * @version 2016/4/25
 * @Desc: HTTP实体
 */
public class HttpEntityUtils2 {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils2.class);

    public static HttpEntity createEntity(Object params, ContentType contentType) {
        if (null == contentType)
            contentType = ContentType.create("text/plain", Consts.UTF_8);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (params != null) {
            logger.debug(params.toString());
            Set<Map.Entry<String, Object>> sets = ((Map<String, Object>)params).entrySet();
            for (Map.Entry<String, Object> entry : sets) {
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
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Consts.UTF_8);
        HttpEntity entity = builder.build();
        return entity;
    }
}
