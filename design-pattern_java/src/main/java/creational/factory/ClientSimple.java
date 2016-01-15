package creational.factory;

import creational.factory.factory.SimpleFactory;
import creational.factory.product.AbstractProduct;

/**
 * Created by verdant on 2016/1/14.
 * Pattern: 简单工厂
 */
public class ClientSimple {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        AbstractProduct productA = simpleFactory.createProduct('A');
        AbstractProduct productB = simpleFactory.createProduct('B');
    }
}
