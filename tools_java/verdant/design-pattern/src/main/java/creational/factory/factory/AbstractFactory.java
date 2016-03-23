package creational.factory.factory;

import creational.factory.product.AbstractProduct;

/**
 * Author: verdant
 * Create: 2016/1/14
 * Desc:   抽象工厂
 */
public interface AbstractFactory {
    AbstractProduct createProduct();
}