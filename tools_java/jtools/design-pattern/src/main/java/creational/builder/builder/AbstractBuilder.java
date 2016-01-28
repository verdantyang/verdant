package creational.builder.builder;

import creational.builder.product.Product;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   抽象生成器
 */
public interface AbstractBuilder {
    void buildPartOne();

    void buildPartTwo();

    Product buildProduct();
}
