package structural.adapter.adaptee;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   需要适配的类
 */
public class Adaptee {

    private static final DebugLog logger = DebugLogFactory.getLogger(Adaptee.class, DesignPatternEnum.Adapter);

    public void specificRequest() {
        logger.log("Handle by " + DebugLog.getClassName(this.getClass()));
    }
}
