package structural.proxy.subject;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化实体
 */
public class RealSubject implements  Subject {
    private static final DebugLog logger = DebugLogFactory.getLogger(RealSubject.class, DesignPatternEnum.Proxy);

    @Override
    public void operate() {
        logger.log("realsubject operate......");
    }
}
