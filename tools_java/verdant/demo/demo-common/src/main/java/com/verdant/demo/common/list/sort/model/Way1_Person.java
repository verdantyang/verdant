package com.verdant.demo.common.list.sort.model;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   列表排序
 * Desc:   方法一（实现Comparable接口的compareTo方法）
 */
public class Way1_Person implements Comparable<Way1_Person> {
    private String name;
    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int compareTo(Way1_Person arg0) {
        return this.getOrder().compareTo(arg0.getOrder());
    }

}
