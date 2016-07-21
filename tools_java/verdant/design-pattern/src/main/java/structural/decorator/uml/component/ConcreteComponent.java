package structural.decorator.uml.component;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * 实例化组件
 *
 * @author verdant
 * @since 2016/04/12
 */
public class ConcreteComponent implements Component {

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteComponent.class, DesignPatternEnum.Decorator);

    @Override
    public void operation() {
        logger.log("Operate by " + DebugLog.getClassName(this.getClass()));
    }
}
