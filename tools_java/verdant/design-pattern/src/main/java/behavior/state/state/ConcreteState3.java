package behavior.state.state;

import behavior.state.context.Context;

/**
 * 状态3
 *
 * @author verdant
 * @since 2016/03/29
 */
public class ConcreteState3 extends State {

    @Override
    public void handlePull(Context c) {
        c.setState(new ConcreteState2());
    }

    @Override
    public void handlePush(Context c) {
        c.setState(new ConcreteState1());
    }

}
