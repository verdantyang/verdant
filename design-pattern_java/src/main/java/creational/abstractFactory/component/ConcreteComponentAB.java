package creational.abstractFactory.component;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 实例化组件AB
 */
public class ConcreteComponentAB implements AbstractComponentA {
    public ConcreteComponentAB(){
        System.out.println("Create -> ConcreteComponentAB");
    }
}