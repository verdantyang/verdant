package com.verdant.jtools.proxy.center.model;


import java.io.Serializable;

/**
 * Copyright (C), 2015-2016 中盈优创
 * InterfaceInfo
 * Author: 龚健
 * Date: 2015/10/12
 */
public class ServiceInfo implements Serializable {


    /**
     * 平台信息
     */
    private Platform platform;

    /**
     * 类名称
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * 发布类型  由serializer  transfer替代
     */
//    @Deprecated
//    private String type;

    /**
     * 序列化类型
     */
    private String serializer;
    /**
     * 传输类型
     */
    private String transfer;
    /**
     * 接口名称
     */
    private String interfaceName;

    private boolean enable;

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ServiceInfo() {
    }

    public ServiceInfo(String name, String version, String serializer, String transfer, String interfaceName, boolean enable) {
        this.name = name;
        this.version = version;
        this.serializer = serializer;
        this.transfer = transfer;
        this.interfaceName = interfaceName;
        this.enable = enable;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "platform=" + platform +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", serializer='" + serializer + '\'' +
                ", transfer='" + transfer + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", enable=" + enable +
                '}';
    }
}
