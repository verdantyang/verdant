package creational.builder.builder;

import creational.builder.product.Product;
import creational.builder.product.ProductA;
import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   实例化生成器A
 */
public class ConcreteBuilderA implements AbstractBuilder{
    Product product;
    public ConcreteBuilderA() {
        product = new ProductA();
    }

    @Override
    public void buildPartOne() {
        DebugLog.print("ProductA -> Build PartOne", DesigiPatternEnum.Builder, ProductA.class);
        product.setPartOne("Build ProductA PartOne");
    }
    @Override
    public void buildPartTwo() {
        DebugLog.print("ProductA -> Build PartTwo", DesigiPatternEnum.Builder, ProductA.class);
        product.setPartTwo("Build ProductA PartTwo");
    }
    @Override
    public Product buildProduct() {
        return product;
    }
}
