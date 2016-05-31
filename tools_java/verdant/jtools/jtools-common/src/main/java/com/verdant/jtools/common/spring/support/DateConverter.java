package com.verdant.jtools.common.spring.support;

import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * DateConverter Spring前后端时间数据转换
 * Author: verdant
 * Create: 2016/03/28
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        return TypeUtils.castToDate(source);
    }
}
