package creational.builder.product;

import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   产品A
 */
public class ProductA extends Product {

    private static final DebugLog logger = DebugLogFactory.getLogger(ProductA.class, DesignPatternEnum.Builder);

    public ProductA() {
        logger.log("Create -> " + DebugLog.getClassName(this.getClass()));
    }
}
