package creational.builder.builder;

import creational.builder.product.Product;
import creational.builder.product.ProductA;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   实例化生成器A
 */
public class ConcreteBuilderA implements AbstractBuilder{

    private static final DebugLog logger = DebugLogFactory.getLogger(ConcreteBuilderA.class, DesignPatternEnum.Builder);

    Product product;
    public ConcreteBuilderA() {
        product = new ProductA();
    }

    @Override
    public void buildPartOne() {
        logger.log("ProductA -> Build PartOne");
        product.setPartOne("Build ProductA PartOne");
    }
    @Override
    public void buildPartTwo() {
        logger.log("ProductA -> Build PartTwo");
        product.setPartTwo("Build ProductA PartTwo");
    }
    @Override
    public Product buildProduct() {
        return product;
    }
}
