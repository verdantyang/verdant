package behavior.state.state;

import behavior.state.context.Context;

/**
 * 状态2
 *
 * @author verdant
 * @since 2016/03/29
 */
public class ConcreteState2 extends State {

    @Override
    public void handlePull(Context c) {
        c.setState(new ConcreteState1());
    }

    @Override
    public void handlePush(Context c) {
        c.setState(new ConcreteState3());
    }

}
