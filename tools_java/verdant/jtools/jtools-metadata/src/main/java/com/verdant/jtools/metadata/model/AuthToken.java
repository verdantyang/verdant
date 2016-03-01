package com.verdant.jtools.metadata.model;

import java.io.Serializable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * Token
 * Author: 龚健
 * Date: 2016/1/13
 */
public class AuthToken implements Serializable {

    private String name;
    private String value;
    private String type;
    private Integer expire;

    public AuthToken() {
    }

    public AuthToken(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public AuthToken(String value, Integer expire) {
        this.value = value;
        this.expire = expire;
    }

    public AuthToken(String name, String value, Integer expire) {
        this.name = name;
        this.value = value;
        this.expire = expire;
    }

    public AuthToken(String name, String value, String type, Integer expire) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.expire = expire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
