package com.verdant.demo.spring.support.spring;


import com.verdant.demo.spring.support.FieldTypeEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * Spring枚举类型转换
 *
 * @author verdant
 * @since 2016/07/06
 */
public class FieldTypeEnumConverter implements Converter<String, FieldTypeEnum> {

    @Override
    public FieldTypeEnum convert(String str) {
        String value = str.trim();
        if ("".equals(value)) {
            return null;
        }
        return FieldTypeEnum.get(Integer.parseInt(value));
    }
}