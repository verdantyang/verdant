package behavior.state.state;

import behavior.state.context.Context;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Desc:   状态2
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
