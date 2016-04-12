package behavior.respChain.Handler;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/11
 * Desc:   实例化处理器2
 */
public class ConcreteHandler2 extends Handler {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteHandler1.class, DesignPatternEnum.ChainOfResponsibility);

    @Override
    public void handleRequest() {
        logger.log("Handle by: " + DebugLog.getClassName(this.getClass()));
        if (getSuccessor() != null) {
            getSuccessor().handleRequest();
        } else {
        }
    }
}
