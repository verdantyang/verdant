package com.jtools.common.utils;

import com.jtools.spring.utils.PropUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.*;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * Copyright (C), 2015-2016 ��ӯ�Ŵ�
 * TemplateUtil
 * Author: ����
 * Date: 2015/10/28
 */
public class TemplateUtil {
    private static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setEncoding(Locale.getDefault(), "UTF-8");
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        configuration.setTemplateLoader(new StringTemplateLoader());
        configuration.setLogTemplateExceptions(false);
    }

    /**
     * 默认可用静态方法
     */
    private static Class[] defaultStaticClasses =
            {
                    IPUtil.class,
                    StringUtil.class,
                    DateUtil.class,
                    Collections3.class,
                    PropUtil.class,
                    String.class,
                    Integer.class,
                    Boolean.class,
                    Float.class,
                    Double.class
            };


    /**
     * 根据参数和模版字符串输出
     * @throws Exception
     */
    public synchronized static String process(Map<String, Object> param, String templateStr) throws Exception {
        ((StringTemplateLoader) configuration.getTemplateLoader()).putTemplate("DEFAULT", templateStr);
        for (Class clazz : defaultStaticClasses) {
            param.put(clazz.getSimpleName(), ((DefaultObjectWrapper) configuration.getObjectWrapper()).getStaticModels().get(clazz.getName()));
        }
        StringWriter writer = new StringWriter();
        Template template = configuration.getTemplate("DEFAULT", "UTF-8");
        configuration.clearTemplateCache();
        template.process(param, writer);
        return writer.toString();
    }


}

