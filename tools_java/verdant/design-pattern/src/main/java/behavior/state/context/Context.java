package behavior.state.context;

import behavior.state.state.State;
import behavior.strategy.strategy.Strategy;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 执行环境
 *
 * @author verdant
 * @since 2016/03/29
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
