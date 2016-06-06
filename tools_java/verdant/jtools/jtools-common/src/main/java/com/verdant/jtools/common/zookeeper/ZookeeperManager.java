package com.verdant.jtools.common.zookeeper;

import com.verdant.jtools.common.pool.config.ZookeeperConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

import java.util.*;

/**
 * Zookeeper管理
 *
 * @author verdant
 * @since 2016/06/02
 */
public class ZookeeperManager {

    Map<String, ZookeeperConfig> resources = new HashMap<>();
    Map<String, CuratorFramework> clients = new HashMap<>();

    /**
     * 设置key和 zk配置
     *
     * @param resources
     */
    public void setResources(Map<String, ZookeeperConfig> resources) {
        this.resources = resources;
    }

    /**
     * 获取client
     *
     * @param key
     * @return
     */
    public CuratorFramework get(String key) {
        return clients.get(key);
    }

    public Map<String, CuratorFramework> getClients() {
        return clients;
    }

    public void setClients(Map<String, CuratorFramework> clients) {
        this.clients = clients;
    }

    /**
     * 注册zk配置
     *
     * @param key
     * @param config
     */
    public void register(String key, ZookeeperConfig config) {
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                config.getConnectString(),
                config.getSessionTimeoutMs(),
                config.getConnectionTimeoutMs(),
                config.getRetryPolicy());
        client.start();
        clients.put(key, client);
    }

    /**
     * 初始化启动
     */
    public void start() {
        CuratorFramework client = null;
        ZookeeperConfig config = null;
        for (String key : resources.keySet()) {
            config = resources.get(key);
            register(key, config);
        }
    }

    /**
     * 关闭销毁
     */
    public void close() {
        for (String key : clients.keySet()) {
            clients.get(key).close();
        }
        clients = null;
    }
}
