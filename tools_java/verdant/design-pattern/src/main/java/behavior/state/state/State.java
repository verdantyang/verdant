package behavior.state.state;

import behavior.state.context.Context;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 抽象状态
 *
 * @author verdant
 * @since 2016/03/29
 */
public abstract class State {
    private static final DebugLog logger = DebugLogFactory.getLogger(State.class, DesignPatternEnum.State);

    public abstract void handlePull(Context c);

    public abstract void handlePush(Context c);

    public void getNow() {
        String stateNow = DebugLog.getClassName(this.getClass());
        logger.log("Now -> " + stateNow);
    }
}
