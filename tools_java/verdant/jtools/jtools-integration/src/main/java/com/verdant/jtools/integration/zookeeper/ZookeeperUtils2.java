package com.verdant.jtools.integration.zookeeper;

import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.integration.zookeeper.client.CuratorClient;
import com.verdant.jtools.integration.zookeeper.config.CuratorConfig;
import com.verdant.jtools.common.pool.way2.AbstractPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用AbstractPool
 *
 * @author verdant
 * @since 2016/06/07
 */
public class ZookeeperUtils2 {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperUtils2.class);

    private static ZookeeperUtils2 INSTACE;
    private static AbstractPool<CuratorClient> pool = new AbstractPool<>();

    static {
        INSTACE = new ZookeeperUtils2();
        pool.setAbstractConfig(new CuratorConfig());
    }

    public static ZookeeperUtils2 getInstance() {
        return INSTACE;
    }

    //通过反射使用Method
    public static void addNodeByMethod(final String nodePath, final String nodeVal) throws Exception {
        pool.execute(CuratorClient.class.getMethod("addNode", String.class, String.class, CuratorClient.class),
                new Object[]{nodePath, nodeVal, null},
                CuratorClient.class);
    }

    //使用回调
    public static void addNode(final String nodePath, final String nodeVal) throws Exception {
        pool.execute(new ICallback<Void, CuratorClient>() {
            @Override
            public Void execute(CuratorClient client) {
                client.addNode(nodePath, nodeVal, client);
                return null;
            }
        }, CuratorClient.class);
    }
}
