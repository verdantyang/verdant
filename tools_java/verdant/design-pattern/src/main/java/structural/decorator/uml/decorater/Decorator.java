package structural.decorator.uml.decorater;

import structural.decorator.uml.component.Component;

/**
 * 抽象装饰者
 *
 * @author verdant
 * @since 2016/04/12
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        // 委派给组件
        component.operation();
    }

}
