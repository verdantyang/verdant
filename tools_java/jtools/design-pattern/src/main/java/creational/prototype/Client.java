package creational.prototype;

import creational.prototype.prototype.ConcretePrototype;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   原型模式（基于Cloneable）
 */
public class Client {
    public static void main(String[] args) {
        ConcretePrototype cp = new ConcretePrototype();
        for (int i = 0; i < 5; i++) {
            ConcretePrototype cpNew = (ConcretePrototype) cp.clone();
            cpNew.valid(cp);
        }
    }
}
