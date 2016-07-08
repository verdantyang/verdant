package com.verdant.jtools.proxy.server.exporter;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface IServiceExporter {

    /**
     * 支持发布的注解
     *
     * @return
     */
    Class<? extends Annotation> supportAnnotationClass();

    /**
     * 处理注解发布类
     *
     * @param objects
     * @throws Exception
     */
    void exporter(Collection objects) throws Exception;


    void doExporter(String beanName, Object bean, Object annotation) throws Exception;

}
