package creational.factory.factory;

import creational.factory.product.AbstractProduct;
import creational.factory.product.ConcreteProductA;
import creational.factory.product.ConcreteProductB;

/**
 * Created by verdant on 2016/1/14.
 */
public class SimpleFactory {
        public AbstractProduct createProduct(int type) {
            switch (type) {
                case 'A':
                    return new ConcreteProductA();

                case 'B':
                    return new ConcreteProductB();

                default:
                    break;
            }
            return null;
        }
}
