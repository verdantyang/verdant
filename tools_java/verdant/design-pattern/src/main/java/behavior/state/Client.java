package behavior.state;


import behavior.state.context.Context;
import behavior.state.state.ConcreteState1;

/**
 * 状态模式
 *
 * @author verdant
 * @since 2016/03/29
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteState1());

        context.push();
        context.push();
        context.push();
    }
}
