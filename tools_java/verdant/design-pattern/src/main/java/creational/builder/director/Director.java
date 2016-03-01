package creational.builder.director;

import creational.builder.builder.AbstractBuilder;
import creational.builder.product.Product;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   调用生成器逐步构造
 */
public class Director {
    public Product constructProduct(AbstractBuilder ab) {
        ab.buildPartOne();
        ab.buildPartTwo();
        return ab.buildProduct();
    }
}
