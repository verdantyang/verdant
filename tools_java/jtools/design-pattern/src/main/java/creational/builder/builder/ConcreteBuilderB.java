package creational.builder.builder;

import creational.builder.product.Product;
import creational.builder.product.ProductA;
import creational.builder.product.ProductB;
import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   实例化生成器B
 */
public class ConcreteBuilderB implements AbstractBuilder{
    Product product;
    public ConcreteBuilderB() {
        product = new ProductB();
    }

    @Override
    public void buildPartOne() {
        DebugLog.print("ProductB -> Build PartOne", DesigiPatternEnum.Builder, ProductA.class);
        product.setPartOne("Build ProductB PartOne");
    }
    @Override
    public void buildPartTwo() {
        DebugLog.print("ProductB -> Build PartTwo", DesigiPatternEnum.Builder, ProductA.class);
        product.setPartTwo("Build ProductB PartTwo");
    }
    @Override
    public Product buildProduct() {
        return product;
    }
}
