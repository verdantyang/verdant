package com.verdant.demo.common.generic;

/**
 * 泛型类型实现
 *
 * @author verdant
 * @since 2016/07/04
 */
public class BaseImplA extends Base<String> {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
