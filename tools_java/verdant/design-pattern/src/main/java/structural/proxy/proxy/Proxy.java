package structural.proxy.proxy;

import structural.proxy.subject.Subject;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   代理实体
 */
public class Proxy implements Subject{

    private static final DebugLog logger = DebugLogFactory.getLogger(Proxy.class, DesignPatternEnum.Proxy);

    private Subject subject;

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void operate() {
        logger.log("before operate......");
        subject.operate();
        logger.log("after operate......");
    }
}
