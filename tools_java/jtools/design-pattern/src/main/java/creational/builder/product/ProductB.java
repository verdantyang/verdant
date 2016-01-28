package creational.builder.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   产品B
 */
public class ProductB extends Product {
    public ProductB() {
        DebugLog.print("Create -> ProductB", DesigiPatternEnum.Builder, ProductA.class);
    }
}
