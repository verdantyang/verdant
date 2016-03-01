package creational.factory;

import creational.factory.factory.SimpleFactory;
import creational.factory.product.AbstractProduct;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Func:   简单工厂模式
 */
public class ClientSimple {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        AbstractProduct productA = simpleFactory.createProduct('A');
        AbstractProduct productB = simpleFactory.createProduct('B');
    }
}
