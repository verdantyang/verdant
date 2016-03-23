package creational.builder.product;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   产品B
 */
public class ProductB extends Product {

    private static final DebugLog logger = DebugLogFactory.getLogger(ProductB.class, DesignPatternEnum.Builder);

    public ProductB() {
        logger.log("Create -> ProductB");
    }
}
