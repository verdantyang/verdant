package com.verdant.demo.common.bytecode.manipulation.assist.proxy;

/**
 * <p>文件名称：ProxyObject.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 优行科技 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>创建日期：2017/2/25</p>
 *
 * @author congyu.yang@geely.com
 * @version 1.0
 */
public class ProxyObject {
    private String name;
    private String value;

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
}
