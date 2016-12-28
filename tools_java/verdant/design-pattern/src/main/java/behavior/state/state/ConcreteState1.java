package behavior.state.state;

import behavior.state.context.Context;

/**
 * 状态1
 *
 * @author verdant
 * @since 2016/03/29
 */
public class ConcreteState1 extends State {

    @Override
    public void handlePull(Context c) {
        c.setState(new ConcreteState3());
    }

    @Override
    public void handlePush(Context c) {
        c.setState(new ConcreteState2());
    }

}
