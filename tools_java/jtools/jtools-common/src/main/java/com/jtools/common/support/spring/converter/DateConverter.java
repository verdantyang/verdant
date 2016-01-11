package com.jtools.common.support.spring.converter;

import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Copyright (C), 2015-2016 ��ӯ�Ŵ�
 * DateConverter
 * Author: ����
 * Date: 2015/10/21
 */
public class DateConverter  implements Converter<String, Date> {
    @Override
    public Date convert(String source)
    {
        return TypeUtils.castToDate(source);
    }
}
