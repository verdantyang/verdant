package creational.builder;

import creational.builder.builder.ConcreteBuilderA;
import creational.builder.builder.ConcreteBuilderB;
import creational.builder.director.Director;
import creational.builder.product.Product;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   生成器模式（将生成过程流程化）
 */
public class Client {
    public static void main(String[] args) {
        Director director = new Director();
        Product pa = director.constructProduct(new ConcreteBuilderA());
        Product pb = director.constructProduct(new ConcreteBuilderB());
    }
}