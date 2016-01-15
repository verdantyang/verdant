package creational.abstractFactory.component;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 实例化组件BA
 */
public class ConcreteComponentBA implements AbstractComponentB {
    public ConcreteComponentBA(){
        System.out.println("Create -> ConcreteComponentBA");
    }
}