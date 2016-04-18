package structural.facade.module;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Desc:   模块1
 */
public class Module1 {

    private static final DebugLog logger = DebugLogFactory.getLogger(Module1.class, DesignPatternEnum.Facade);

    public void execute() {
        logger.log("Handle by " + DebugLog.getClassName(this.getClass()));
    }
}
