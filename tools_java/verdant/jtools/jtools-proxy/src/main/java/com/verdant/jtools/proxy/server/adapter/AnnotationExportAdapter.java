package com.verdant.jtools.proxy.server.adapter;

import com.commons.proxy.server.exporter.IServiceExporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.Map;

//BeanDefinitionRegistryPostProcessor 常用于注册Bean
public class AnnotationExportAdapter extends ApplicationObjectSupport implements BeanDefinitionRegistryPostProcessor {

    //服务发布
    private IServiceExporter serviceExporter;

    public void setServiceExporter(IServiceExporter serviceExporter) {
        this.serviceExporter = serviceExporter;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ApplicationContext ac = getApplicationContext().getParent();
        //扫描ServiceExporter支持的注解
        Map<String, Object> beans = ac.getBeansWithAnnotation(serviceExporter.supportAnnotationClass());
        try {
            serviceExporter.exporter(beans.values());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }


}
