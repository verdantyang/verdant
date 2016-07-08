package com.verdant.jtools.proxy.center.model;

import com.commons.common.utils.ContextUtil;
import com.commons.common.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 每个发布的War包含一组配置 存放在zk中
 */
public class Platform implements Serializable {
    public Platform() {
    }

    public Platform(String protocol, String ip, String port, String domain, Double weight) {
        this.ip = ip;
        this.port = port;
        this.contextPath = ContextUtil.getWebAppContext().getServletContext().getContextPath().replaceAll("/", "");
        this.domain = domain;
        this.protocol = protocol;
        this.weight = weight;
    }

    public Platform(Platform platform) {
        if (platform != null) {
            this.ip = platform.getIp();
            this.port = platform.getPort();
            this.contextPath = platform.getContextPath();
            this.domain = platform.getDomain();
            this.protocol = platform.getProtocol();
            this.weight = platform.getWeight();
        }
    }

    /**
     * 按平台获取代理前缀
     *
     * @return URL http://xxx.xxx.xx.xx:8080/abc/
     */
    public String getUrl() {
        StringBuffer urlPrefix = new StringBuffer();
        urlPrefix.append(protocol.toLowerCase());
        urlPrefix.append("://");
        urlPrefix.append(ip);
        urlPrefix.append(":");
        urlPrefix.append(port);
        urlPrefix.append("/");
        if (!StringUtil.isEmpty(contextPath)) {
            urlPrefix.append(contextPath);
            urlPrefix.append("/");
        }
        return urlPrefix.toString();
    }

    public String getIdentity() {

        StringBuffer urlPrefix = new StringBuffer();
        urlPrefix.append(domain);
        urlPrefix.append("|");
        urlPrefix.append(ip);
        urlPrefix.append("|");
        urlPrefix.append(port);
        urlPrefix.append("|");
        urlPrefix.append(StringUtil.isEmpty(contextPath) ? "" : contextPath);
        return urlPrefix.toString();
    }

    /**
     * 服务协议
     */
    private String protocol;
    /**
     * 服务ip
     */
    private String ip;

    /**
     * 服务端口
     */
    private String port;

    /**
     * 服务路径
     */
    private String contextPath;

    /**
     * 服务域
     * 区分不同项目的标识 proxy.properties配置文件中设置
     */
    private String domain;

    /**
     * 按权重策略时 按weight/总占比 随机
     */
    private Double weight;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 所属服务
     */
    List<ServiceInfo> services;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<ServiceInfo> getServices() {
        return services;
    }

    public void setServices(List<ServiceInfo> services) {
        if (this.services != null && !this.services.isEmpty()) {
            Set<ServiceInfo> info=new HashSet<>();
            info.addAll(this.services);
            info.addAll(services);
            this.services=new ArrayList<>(info);
        }else{
            this.services = services;
        }
    }

    @Override
    public String toString() {
        return "Platform{" +
                "protocol='" + protocol + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", domain='" + domain + '\'' +
                ", weight=" + weight +
                ", services=" + services +
                '}';
    }
}
