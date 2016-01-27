package creational.prototype;

import creational.prototype.prototype.ConcretePrototype;

/**
 * Created by Administrator on 2016/1/27.
 */
public class Client implements Cloneable {
    public static void main(String[] args) {
        ConcretePrototype cp = new ConcretePrototype();
        for (int i = 0; i < 5; i++) {
            ConcretePrototype cpNew = (ConcretePrototype) cp.clone();
            cpNew.valid(cp);
        }
    }
}
