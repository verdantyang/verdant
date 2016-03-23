package creational.builder.builder;

import creational.builder.product.Product;
import creational.builder.product.ProductB;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   实例化生成器B
 */
public class ConcreteBuilderB implements AbstractBuilder{

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteBuilderB.class, DesignPatternEnum.Builder);

    Product product;
    public ConcreteBuilderB() {
        product = new ProductB();
    }

    @Override
    public void buildPartOne() {
        logger.log("ProductB -> Build PartOne");
        product.setPartOne("Build ProductB PartOne");
    }
    @Override
    public void buildPartTwo() {
        logger.log("ProductB -> Build PartTwo");
        product.setPartTwo("Build ProductB PartTwo");
    }
    @Override
    public Product buildProduct() {
        return product;
    }
}
