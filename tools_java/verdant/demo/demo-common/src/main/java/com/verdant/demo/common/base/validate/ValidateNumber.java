package com.verdant.demo.common.base.validate;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   数字类型检查（int、Integer、String）
 */
public class ValidateNumber {
    public static void main(String[] args) {
        String str1 = "78";
        String str2 = "19.76";
        String str3 = "abc";
        Assert.assertEquals(NumberUtils.isDigits(str1), true);  //判断str是否整数， true-整数  false-非整数
        Assert.assertEquals(NumberUtils.isDigits(str2), false);
        Assert.assertEquals(NumberUtils.isNumber(str2), true);  //判断str是否数字（整数、小数、科学计数法等等格式）
        Assert.assertEquals(NumberUtils.isNumber(str3), false);
    }
}
