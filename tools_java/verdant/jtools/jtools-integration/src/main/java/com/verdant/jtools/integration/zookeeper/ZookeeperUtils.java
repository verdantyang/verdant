package com.verdant.jtools.integration.zookeeper;

import com.verdant.jtools.common.pool.ICallback;
import com.verdant.jtools.integration.zookeeper.client.CuratorClient;
import com.verdant.jtools.integration.zookeeper.config.CuratorConfig;
import com.verdant.jtools.common.pool.way1.IFactory;
import com.verdant.jtools.common.pool.way1.IPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用IPool
 *
 * @author verdant
 * @since 2016/06/07
 */
public class ZookeeperUtils {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperUtils.class);

    private static ZookeeperUtils INSTACE;
    private static IPool<CuratorClient> pool;

    static {
        INSTACE = new ZookeeperUtils();
        pool = new IPool.Builder<CuratorClient>(new IFactory() {
            @Override
            public CuratorClient create() {
                return new CuratorClient(new CuratorConfig());
            }
        }).softReferences().build();
    }

    public static ZookeeperUtils getInstance() {
        return INSTACE;
    }

    public CuratorClient getClient() {
        return pool.borrow();
    }

    public void releaseClient(CuratorClient client) {
        pool.release(client);
    }

    public void addNode(final String nodePath, final String nodeVal) {
        pool.run(new ICallback<Void, CuratorClient>() {
            @Override
            public Void execute(CuratorClient client) {
                client.addNode(nodePath, nodeVal, client);
                return null;
            }
        });
    }
}
