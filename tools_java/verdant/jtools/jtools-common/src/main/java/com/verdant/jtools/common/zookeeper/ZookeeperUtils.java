package com.verdant.jtools.common.zookeeper;

import com.verdant.jtools.common.pool.AbstractPool;
import com.verdant.jtools.common.pool.config.ZookeeperConfig;

/**
 * Created by Administrator on 2016/6/6.
 */
public class ZookeeperUtils {
    private static ZookeeperUtils INSTACE;
    private static AbstractPool<ZookeeperClient> pool = new AbstractPool<>();

    static {
        INSTACE = new ZookeeperUtils();
        pool.setAbstractConfig(new ZookeeperConfig());
    }

    public static ZookeeperUtils getInstance() {
        return INSTACE;
    }

    public static void addNode(String nodePath, String nodeVal) throws Exception {
        pool.execute(ZookeeperClient.class.getMethod("addNode", String.class, String.class),
                new Object[]{nodePath, nodeVal},
                ZookeeperClient.class);
    }
}
