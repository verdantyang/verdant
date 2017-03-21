package com.verdant.jtools.integration.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZK实现的代理注册
 * Copyright (C), 2015-2016 中盈优创
 * ZookeeperProxyService
 * Date: 2015/10/13
 */
public class ProxyRegistry {

    final static Logger logger = LoggerFactory.getLogger(ProxyRegistry.class);
    String nodePath = "/rps/dynamic/epl";
    String nodeVal = "abc";

    public void usePool2() throws Exception {
        ZookeeperUtils2.getInstance().addNode(nodePath, nodeVal);
        System.out.println(nodePath);
    }

    public void usePool1() {
        ZookeeperUtils.getInstance().addNode(nodePath, nodeVal);
        System.out.println(nodePath);
    }

    public static void main(String[] args) throws Exception {
        ProxyRegistry usage = new ProxyRegistry();
        usage.usePool1();
    }
}
