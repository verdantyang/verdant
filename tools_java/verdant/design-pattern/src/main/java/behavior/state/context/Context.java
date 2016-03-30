package behavior.state.context;

import behavior.state.state.State;
import behavior.strategy.strategy.Strategy;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Desc:   执行环境
 */
public class Context {

    private static final DebugLog logger = DebugLogFactory.getLogger(Context.class, DesignPatternEnum.State);

    private static final String format = "%s to %s";
    private State state = null;

    public Context() {

    }

    public Context(State state) {
        this.state = state;
    }

    public void setState(State state) {
        String stateOld;
        if (null == this.state)
            stateOld = "Null";
        else
            stateOld = DebugLog.getClassName(this.state.getClass());
        this.state = state;
        String stateNew = DebugLog.getClassName(this.state.getClass());
        logger.log(String.format(format, stateOld, stateNew));
    }

    public void push() {
        state.handlePush(this);
        state.getNow();
    }

    public void pull() {
        state.handlePull(this);
        state.getNow();
    }
}
