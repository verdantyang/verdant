package com.spring.demo.web.auth;

import java.io.Serializable;

/**
 * Token 用户认证信息
 * Author: verdant
 * Date: 2016/1/13
 */
public class AuthToken implements Serializable {

    private String cookiePrefix;
    private String cachePrefix;
    private String value;
    private Integer expire;

    public String getCookiePrefix() {
        return cookiePrefix;
    }

    public void setCookiePrefix(String cookiePrefix) {
        this.cookiePrefix = cookiePrefix;
    }

    public String getCachePrefix() {
        return cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }
}
