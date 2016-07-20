package structural.decorator.uml.decorater;

import structural.decorator.uml.component.Component;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   抽象装饰者
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component){
        this.component = component;
    }

    @Override
    public void operation() {
        // 委派给组件
        component.operation();
    }

}
