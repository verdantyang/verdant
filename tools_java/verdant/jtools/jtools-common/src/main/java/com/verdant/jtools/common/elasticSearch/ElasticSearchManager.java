package com.verdant.jtools.common.elasticSearch;

import com.verdant.jtools.common.generic.AbstractManager;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * EsManager
 *
 * @author verdant
 * @since 2015/12/3
 */
public class ElasticSearchManager extends AbstractManager {
    Map<String, ElasticSearchConfiguration> resources = new HashMap<>();
    Map<String, Client> clients = new HashMap<>();

    /**
     * 设置key和 es配置
     *
     * @param resources
     */
    public void setResources(Map<String, ElasticSearchConfiguration> resources) {
        this.resources = resources;
    }

    /**
     * 获取client
     *
     * @param key
     * @return
     */
    public Client get(String key) {
        return clients.get(key);
    }

    /**
     * 注册zk配置
     *
     * @param key
     * @param config
     */
    public void register(String key, ElasticSearchConfiguration config) {
        Settings.Builder settingBuilder = Settings.builder();
        settingBuilder.put(config.getParams());
        TransportClient.Builder clientBuilder = TransportClient.builder();
        clientBuilder.settings(settingBuilder);
        Client client = clientBuilder.build();
        ((TransportClient) client).addTransportAddress(config.getTransportAddress());
        clients.put(key, client);

    }

    /**
     * 初始化启动
     */
    @Override
    public void start() {
        ElasticSearchConfiguration config = null;
        for (String key : resources.keySet()) {
            config = resources.get(key);
            register(key, config);
        }
    }

    /**
     * 关闭销毁
     */
    @Override
    public void close() {
        clients = null;
    }
}
