package com.verdant.jtools.common.zookeeper;

import com.verdant.jtools.common.web.factory.HttpClientFacory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * 注入zkClient调用者发布和接受数据
 * ZkConfigManager
 * Date: 2015/10/13
 */
public class ZookeeperUtils2 {
    private static CloseableHttpClient httpClient = HttpClientFacory.getInstance();
    private String host;
    private String rootPath;
    private String providePath;

    private String managerKey;
    private ZookeeperManager zookeeperManager;
    private CuratorFramework client;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
    /**
     * 获取提供方ZK路径
     */
    public String getProvidePath() {
        return getRootPath() + this.providePath;
    }

    public void setProvidePath(String providePath) {
        this.providePath = providePath;
    }

    public CuratorFramework getClient() {
        if (client == null) {
            client = zookeeperManager.get(managerKey);
        }
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    public ZookeeperManager getZookeeperManager() {
        return zookeeperManager;
    }

    public void setZookeeperManager(ZookeeperManager zookeeperManager) {
        this.zookeeperManager = zookeeperManager;
    }

    public String getManagerKey() {
        return managerKey;
    }

    public void setManagerKey(String managerKey) {
        this.managerKey = managerKey;
    }

}
