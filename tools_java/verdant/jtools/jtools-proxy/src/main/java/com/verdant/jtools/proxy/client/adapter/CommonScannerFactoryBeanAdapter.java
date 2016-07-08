package com.verdant.jtools.proxy.client.adapter;

import com.commons.proxy.center.model.ProxyConfigure;
import com.commons.proxy.center.provide.IProxyProvider;
import com.commons.proxy.client.core.factory.CommonProxyFactoryBean;
import com.commons.proxy.client.hessian.DistributeHessianProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 扫描类根据配置创建代理的FactoryBean
 */
public class CommonScannerFactoryBeanAdapter extends ClassPathBeanDefinitionScanner {

    //实现发布代理工厂接口
    private Class<? extends FactoryBean> proxyBeanClass;

    //默认最高限定版本接口
    private Float maxVersion = -1f;

    //默认的url前缀
    private String defaultUrlPrefix;

    //服务中心接口
    private IProxyProvider proxyProvider;

    public CommonScannerFactoryBeanAdapter(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void setProxyProvider(IProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    public void setDefaultUrlPrefix(String defaultUrlPrefix) {
        this.defaultUrlPrefix = defaultUrlPrefix;
    }

    public void setMaxVersion(Float maxVersion) {
        this.maxVersion = maxVersion;
    }

    public void setProxyBeanClass(Class<? extends FactoryBean> proxyBeanClass) {
        this.proxyBeanClass = proxyBeanClass;
    }



    /**
     * 过滤扫描配置
     */
    public void registerFilters(List<String> excludes) {

        boolean acceptAllInterfaces = true;

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter(new TypeFilter() {
                @Override
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return true;
                }
            });
        }

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });

        //exclude regex path
        if (excludes != null && !excludes.isEmpty()) {
            for (String expression : excludes) {
                addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(expression)));
            }
        }
    }

    /**
     * 扫描
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as ProxyFactoryBeans
     * Such as HessianProxyFactoryBean
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No Proxy Interface was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    /**
     * 处理扫描接口
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            if (logger.isDebugEnabled()) {
                logger.debug("Creating FactoryBean with name '" + holder.getBeanName()
                        + "' and '" + definition.getBeanClassName());
            }
            String interfaceName = definition.getBeanClassName();

            String defaultImplClassName = interfaceName.substring(interfaceName.lastIndexOf(".") + 2);
            if (proxyProvider == null) {
                //默认接口去掉I为类名
                processProxyBeanDefinitions(definition, interfaceName, defaultUrlPrefix + defaultImplClassName);
            } else {
                String serviceUrl = "http://proxy/" + defaultImplClassName;
                processProxyBeanDefinitions(definition, interfaceName, serviceUrl);
            }
        }
    }

    /**
     * 按类型控制发布实现
     */
    private void processProxyBeanDefinitions(GenericBeanDefinition definition, String interfaceName, String implClassName) {

        definition.setBeanClass(this.proxyBeanClass);
        if (this.proxyBeanClass == DistributeHessianProxyFactoryBean.class) {
            processHessianProxyDefinition(definition, interfaceName, implClassName);
        }
        if (this.proxyBeanClass == CommonProxyFactoryBean.class) {
            processCommonProxyDefinition(definition, interfaceName, implClassName);
        }
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

    }

    /**
     * 设置Hessian定义自动适配common配置
     */
    private void processCommonProxyDefinition(GenericBeanDefinition definition, String interfaceName, String defaultServiceUrl) {
        //for RemoteAccessor
        try {
            definition.getPropertyValues().add("serviceInterface", interfaceName);

            ProxyConfigure configure = new ProxyConfigure();
            configure.setServiceUrl(defaultServiceUrl);
            configure.setServiceInterfaceName(interfaceName);
            configure.setServiceInterface(Class.forName(interfaceName));
            configure.setMaxVersion(maxVersion);
            definition.getPropertyValues().add("configure", configure);
            definition.getPropertyValues().add("proxyProvider", proxyProvider);
        } catch (ClassNotFoundException e) {
            logger.error("proxy interface not found", e);
        }

    }

    /**
     * 设置Hessian定义代理配置
     */
    private void processHessianProxyDefinition(GenericBeanDefinition definition, String interfaceName, String defaultServiceUrl) {
        definition.getPropertyValues().add("connectTimeout", 5 * 1000);
        definition.getPropertyValues().add("readTimeout", 60 * 1000);
        definition.getPropertyValues().add("chunkedPost", false);
        definition.getPropertyValues().add("overloadEnabled", true);
        definition.getPropertyValues().add("serviceUrl", defaultServiceUrl);
        definition.getPropertyValues().add("serviceInterface", interfaceName);
        definition.getPropertyValues().add("hessian2", true);

        //路由配置
        definition.getPropertyValues().add("serviceInterfaceName", interfaceName);
        definition.getPropertyValues().add("maxVersion", maxVersion);
        definition.getPropertyValues().add("proxyProvider", proxyProvider);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
