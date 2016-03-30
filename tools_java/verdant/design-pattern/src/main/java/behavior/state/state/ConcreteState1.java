package behavior.state.state;

import behavior.state.context.Context;

/**
 * Author: verdant
 * Create: 2016/3/29
 * Desc:   状态1
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
