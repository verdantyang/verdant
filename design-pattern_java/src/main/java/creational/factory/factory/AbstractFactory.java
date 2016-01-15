package creational.factory.factory;

import creational.factory.product.AbstractProduct;

/**
 * Created by verdant on 2016/1/14.
 * Desc: 抽象工厂
 */
public interface AbstractFactory {
    public AbstractProduct createProduct();
}