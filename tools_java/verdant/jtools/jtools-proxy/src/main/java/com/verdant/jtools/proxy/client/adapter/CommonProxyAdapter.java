package com.verdant.jtools.proxy.client.adapter;

import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.client.hessian.DistributeHessianProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * client扫描代理
 * 创建ProxyServiceScanner扫描需要代理的类
 */
public class CommonProxyAdapter extends ApplicationObjectSupport implements BeanDefinitionRegistryPostProcessor {


    //扫描路径
    private String basePackage;

    //代理接口的实现类
    private Class<? extends FactoryBean> proxyBeanClass = DistributeHessianProxyFactoryBean.class;

    //排除路径正则
    List<String> excludes;

    //默认最高限定版本接口
    private Float maxVersion = -1f;

    //默认的url前缀
    private String defaultUrlPrefix;

    //服务中心接口
    private IProxyProvider proxyProvider;

    public void setProxyProvider(IProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    public void setDefaultUrlPrefix(String defaultUrlPrefix) {
        this.defaultUrlPrefix = defaultUrlPrefix;
    }

    public void setMaxVersion(Float maxVersion) {
        this.maxVersion = maxVersion;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setProxyBeanClass(Class<? extends FactoryBean> proxyBeanClass) {
        this.proxyBeanClass = proxyBeanClass;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        CommonScannerFactoryBeanAdapter scanner = new CommonScannerFactoryBeanAdapter(registry);
        scanner.registerFilters(excludes);
        scanner.setProxyBeanClass(proxyBeanClass);
        scanner.setProxyProvider(proxyProvider);
        scanner.setDefaultUrlPrefix(defaultUrlPrefix);
        scanner.setMaxVersion(maxVersion);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //unused
    }
}
