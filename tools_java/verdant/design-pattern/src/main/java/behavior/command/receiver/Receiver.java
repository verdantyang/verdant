package behavior.command.receiver;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Desc:   命令接受者
 */
public class Receiver {

    private static final DebugLog logger = DebugLogFactory.getLogger(Receiver.class, DesignPatternEnum.Command);

    public void operate1() {
        logger.log("Execute -> operate1");
    }

    public void operate2() {
        logger.log("Execute -> operate2");
    }
}
