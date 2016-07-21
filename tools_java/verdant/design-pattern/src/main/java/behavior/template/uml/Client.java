package behavior.template.uml;


import behavior.template.uml.tmpl.ConcreteTemplate1;
import behavior.template.uml.tmpl.ConcreteTemplate2;

/**
 * 模板模式
 *
 * @author verdant
 * @since 2016/04/12
 */
public class Client {
    public static void main(String[] args) {
        ConcreteTemplate1 concreteTemplate1 = new ConcreteTemplate1();
        concreteTemplate1.process();
        ConcreteTemplate2 concreteTemplate2 = new ConcreteTemplate2();
        concreteTemplate2.process();
    }
}
