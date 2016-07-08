package com.verdant.jtools.proxy.center.route.impl;

import com.alibaba.fastjson.JSON;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.Platform;
import com.commons.proxy.center.model.ServiceInfo;
import com.commons.proxy.center.route.IProxyRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路由管理实现
 * Copyright (C), 2015-2016 中盈优创
 * ProxyRoute
 * Author: 龚健
 * Date: 2015/10/15
 */
public class LocalProxyRoute implements IProxyRoute {
    final static Logger logger = LoggerFactory.getLogger(LocalProxyRoute.class);

    private final int ADD = 1;
    private final int REMOVE = 2;

    /**
     * key:域#服务接口名称 value:服务集合
     */
    Map<String, List<ServiceInfo>> serviceInfoMap;

    /**
     * key：路径 value:原始平台数据
     */
    private Map<String, Platform> platforms;


    /**
     * 保留已解析路径集合
     */
    private List<String> currentPaths;


    public List<String> getCurrentPaths() {
        if (currentPaths == null)
            currentPaths = new ArrayList<>();
        return currentPaths;
    }

    /**
     * Map缓存服务便于查找
     */
    public Map<String, List<ServiceInfo>> getServices() {
        if (serviceInfoMap == null)
            serviceInfoMap = new HashMap<>();
        return serviceInfoMap;
    }

    /**
     * 存储原始数据
     */
    public Map<String, Platform> getPlatforms() {
        if (platforms == null)
            platforms = new HashMap<>();
        return platforms;
    }

    @Override
    public List<ServiceInfo> getServices(String domain, String interfaceName, float maxVersion) throws ServiceException {
        return getServices(domain, interfaceName, null, maxVersion);
    }

    /**
     * 按需要注入的 接口实现类名称 查找url
     */
    public List<ServiceInfo> getServices(String domain, String interfaceName, String proxyType, float maxVersion) throws ServiceException {
        //pss#com.abc.IUserService
        List<ServiceInfo> services = getServices().get(domain + "#" + interfaceName);
        List<ServiceInfo> filter = new ArrayList<>();
        List<ServiceInfo> effective = new ArrayList<>();
        float foundMaxVersion = -1f;
        if (services != null) {
            for (ServiceInfo service : services) {
                //代理类型匹配 && 版本不超限 &&可用
                if ((proxyType == null || service.getTransfer().equals(proxyType)) && service.isEnable()) {
                    float version = Float.valueOf(service.getVersion());
                    if (version >= foundMaxVersion) {//过滤最高版本
                        foundMaxVersion = version;
                    }
                    //不限制版本号  或 服务版本号  大于等于 maxVersion=最低要求版本号
                    if (maxVersion == -1f || Float.valueOf(service.getVersion()) >= maxVersion) {
                        filter.add(service);
                    }
                }
            }
        }
        for (ServiceInfo service : filter) {
            if (Float.valueOf(service.getVersion()) == foundMaxVersion) {
                effective.add(service);
            }
        }
        //返回不超过限制的最高版本的集合
        return effective;
    }

    /**
     * 更新代理数据  控制 platforms 和 serviceInfoMap
     *
     * @param path
     * @param platform
     * @param flag
     */
    private void updateProxyInfo(String path, Platform platform, int flag) throws ServiceException {
        String domain = getDomainByPath(path);
        List<ServiceInfo> services = new ArrayList<>(platform.getServices());
        Platform basePfInfo = new Platform(platform);
        for (ServiceInfo service : services) {
            service.setPlatform(basePfInfo);
            //服务查找标识：域#接口名称
            String serviceKey = domain + "#" + service.getInterfaceName();
            if (getServices().get(serviceKey) == null) {
                getServices().put(serviceKey, new ArrayList<ServiceInfo>());
            }
            //添加|删除 服务
            if (flag == ADD) {
                getServices().get(serviceKey).add(service);
            } else if (flag == REMOVE) {
                if (service.getPlatform().getIdentity().equals(basePfInfo.getIdentity())) {
                    getServices().get(serviceKey).remove(service);
                }
            }
        }
        //添加|删除 平台数据
        if (flag == ADD) {
            logger.debug("proxy route add platform cache {}", platform);
            getPlatforms().put(path, platform);
        } else if (flag == REMOVE) {
            logger.debug("proxy route remove platform {}", path);
            getPlatforms().remove(path);
        }

    }

    /**
     * 添加或删除新的路径和数据到路由管理中
     *
     * @param path    新的路径
     * @param content 路径下内容
     */
    public void addOrUpdate(String path, String content) throws ServiceException {
        //校验重复
        if (!getCurrentPaths().contains(path)) {
            getCurrentPaths().add(path);
        }
        Platform platform = getPlatforms().get(path);
        if (platform != null) {
            remove(path);
        }
        try {
            platform = JSON.parseObject(content, Platform.class);
            this.updateProxyInfo(path, platform, ADD);
        } catch (Exception e) {
            logger.error("update local proxy info fail", e);
        }
    }

    /**
     * 删除下线的节点路径 及数据
     */
    public void remove(String path) throws ServiceException {
        getCurrentPaths().remove(path);
        Platform platform = getPlatforms().get(path);
        if (platform != null) {
            this.updateProxyInfo(path, platform, REMOVE);
        }

    }

    /**
     * 根据zkpath按格式获取 domain
     */
    private String getDomainByPath(String path) {
        String domain = path.split("\\|")[0];
        return domain;
    }

}
