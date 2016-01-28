package creational.builder.product;

import utils.DebugLog;
import utils.DesigiPatternEnum;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Desc:   产品A
 */
public class ProductA extends Product {
    public ProductA() {
        DebugLog.print("Create -> ProductA", DesigiPatternEnum.Builder, ProductA.class);
    }
}
