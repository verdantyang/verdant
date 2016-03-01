package com.verdant.jtools.cache.manager;

import java.util.Map;

public class CommonCacheManager {
    private ICacheManager defaultManager;           //默认管理器
    private Map<String, ICacheManager> managers;    //用于集成异构系统缓存（暂时没用）

    public void setDefaultManager(ICacheManager defaultManager) {
        this.defaultManager = defaultManager;
    }

    public void setManagers(Map<String, ICacheManager> managers) {
        this.managers = managers;
    }

    public ICacheManager getManager(String key) {
        if (managers == null || !managers.containsKey(key) || key.toLowerCase().equals("default")) {
            return defaultManager;
        }
        return managers.get(key);
    }

}
