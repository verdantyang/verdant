package com.verdant.jtools.common.elasticSearch;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * ElasticSearchConfiguration
 *
 * @author verdant
 * @since 2015/12/3
 */
public class ElasticSearchConfiguration implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfiguration.class);
    private String ip;
    private Integer port;
    private Map<String, String> params;

    public InetSocketTransportAddress getTransportAddress() {
        try {
            return new InetSocketTransportAddress(InetAddress.getByName(ip), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error("error initialize elasticsearch  {}:{} , InetAddress.getByName catch UnknownHost", ip, port);
            return null;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
