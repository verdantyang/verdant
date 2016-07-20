package structural.decorator.uml.decorater;

import structural.decorator.uml.component.Component;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化装饰者A
 */
public class ConcreteDecoratorA extends Decorator {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteDecoratorA.class, DesignPatternEnum.Decorator);

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        logger.log("Decorate by " + DebugLog.getClassName(this.getClass()));
        super.operation();
        // 写相关的业务代码
    }
}
