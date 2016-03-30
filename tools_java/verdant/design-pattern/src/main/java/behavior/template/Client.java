package behavior.template;

import behavior.template.template.ConcreteTemplate1;
import behavior.template.template.ConcreteTemplate2;

/**
 * Author: verdant
 * Create: 2016/3/30
 * Func:   模板模式
 */
public class Client {
    public static void main(String[] args) {
        ConcreteTemplate1 concreteTemplate1 = new ConcreteTemplate1();
        concreteTemplate1.process();
        ConcreteTemplate2 concreteTemplate2 = new ConcreteTemplate2();
        concreteTemplate2.process();
    }
}
