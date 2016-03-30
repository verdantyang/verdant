package behavior.state;


import behavior.state.context.Context;
import behavior.state.state.ConcreteState1;

/**
 * Author: verdant
 * Create: 2016/3/23
 * Func:   状态模式
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteState1());

        context.push();
        context.push();
        context.push();
    }
}
