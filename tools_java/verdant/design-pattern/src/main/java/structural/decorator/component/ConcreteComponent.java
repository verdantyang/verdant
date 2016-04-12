package structural.decorator.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化组件
 */
public class ConcreteComponent implements Component {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponent.class, DesignPatternEnum.Decorator);

    @Override
    public void operation() {
        logger.log("Operate by " + DebugLog.getClassName(this.getClass()));
    }
}
