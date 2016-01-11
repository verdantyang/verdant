package com.jtools.common.support.spring.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.zookeeper.data.Stat;

import java.util.*;

/**
 * Copyright (C), 2015-2016 中盈优创
 * ZookeeperManager
 * Author: 龚健
 * Date: 2015/11/12
 */
public class ZookeeperManager {


    Map<String, ZookeeperConfiguration> resources = new HashMap<>();
    Map<String, CuratorFramework> clients = new HashMap<>();

    /**
     * 设置key和 zk配置
     * @param resources
     */
    public void setResources(Map<String, ZookeeperConfiguration> resources) {
        this.resources = resources;
    }

    /**
     * 获取client
     * @param key
     * @return
     */
    public CuratorFramework get(String key){
        return clients.get(key);
    }

    /**
     * 注册zk配置
     * @param key
     * @param config
     */
    public void register(String key,ZookeeperConfiguration config){
        CuratorFramework client = CuratorFrameworkFactory.newClient(config.getConnectString(),config.getSessionTimeoutMs(),config.getConnectionTimeoutMs(),config.getRetryPolicy());
        client.start();
        clients.put(key,client);
    }

    /**
     * 初始化启动
     */
    public void start() {
        CuratorFramework client = null;
        ZookeeperConfiguration config=null;
        for (String key : resources.keySet()) {
            config = resources.get(key);
            register(key,config);
        }
    }

    /**
     * 关闭销毁
     */
    public void close(){
        for (String key : clients.keySet()) {
            clients.get(key).close();
        }
        clients=null;
    }
}
