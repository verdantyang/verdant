package com.verdant.jtools.sched.quartz;


import com.verdant.jtools.sched.annotation.CronJob;
import com.verdant.jtools.common.spring.utils.SpringPropUtils;
import org.quartz.Trigger;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时任务Bean
 * 一个CronTriggerFactoryBean对应一个SpringMethodInvokingJobDetailFactoryBean
 */
public class QuartzSchedulerFactoryBean extends SchedulerFactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerFactoryBean.class);

    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));
    private ApplicationContext applicationContext;
    private DefaultListableBeanFactory acf;
    List<Trigger> triggersList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setConfigLocation(Resource configLocation) {
        super.setConfigLocation(configLocation);
        coverName(configLocation);
    }

    /**
     * 设置BeanName ,覆盖 schedulerName（schedulerName为org.quartz.scheduler.instanceName）
     * 运行时通过SCHED_NAME(instanceName)过滤需要执行的Job
     *
     * @param configLocation quartz.properties
     */
    private void coverName(Resource configLocation) {
        try {
            Properties mergedProps = new Properties();
            PropertiesLoaderUtils.fillProperties(mergedProps, configLocation);
            String name = mergedProps.getProperty(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME);
            setBeanName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过反射获取triggers属性的内容
        acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Field field = ReflectionUtils.findField(SchedulerFactoryBean.class, "triggers");
        ReflectionUtils.makeAccessible(field);
        Object object = ReflectionUtils.getField(field, this);
        triggersList = (List<Trigger>) object;
        if (triggersList == null) {
            triggersList = new ArrayList<>();
        }
        //以回调的方式添加工程中定义的定时任务
        for (String beanName : this.applicationContext.getBeanDefinitionNames()) {
            if (beanName.startsWith(QuartzSchedulerFactoryBean.class.getName())) {
                continue;
            }
            final Object bean = this.applicationContext.getBean(beanName);
            Class targetClass = AopUtils.getTargetClass(bean);
            if (!this.nonAnnotatedClasses.contains(targetClass)) {
                final LinkedHashSet annotatedMethods = new LinkedHashSet(1);
                ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                    @Override
                    public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                        CronJob jobAnnotation = AnnotationUtils.getAnnotation(method, CronJob.class);
                        if (jobAnnotation != null) {
                            processJob(jobAnnotation, method, bean);
                            annotatedMethods.add(method);
                        }
                    }
                });
                if (annotatedMethods.isEmpty()) {
                    this.nonAnnotatedClasses.add(targetClass);
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("No @CronJob annotations found on bean class: " + bean.getClass());
                    }
                } else if (this.logger.isDebugEnabled()) {
                    this.logger.debug(annotatedMethods.size() + " @CronJob methods processed on bean \'" + bean.getClass() + "\': " + annotatedMethods);
                }
            }
        }
        if (!triggersList.isEmpty()) {
            ReflectionUtils.setField(field, this, triggersList);
        }

        super.afterPropertiesSet();

        //后期需动态添加定时任务时使用此实例扩展工具
        SchedulerRepository repository = SchedulerRepository.getInstance();
    }

    private void processJob(CronJob jobAnnotation, Method method, Object bean) {

        Class targetClass = AopUtils.getTargetClass(bean);
        String classMethodName = targetClass.getSimpleName() + "_" + method.getName().substring(0, 1).toUpperCase() + method.getName().substring(1);
        String jobBeanName = classMethodName + "Job";

        BeanDefinitionBuilder jobDetailBuilder = BeanDefinitionBuilder.genericBeanDefinition(SpringMethodInvokingJobDetailFactoryBean.class);
        jobDetailBuilder.addPropertyValue("targetBeanName", targetClass.getSimpleName().substring(0, 1).toLowerCase() + targetClass.getSimpleName().substring(1));
        jobDetailBuilder.addPropertyValue("targetMethod", method.getName());
        jobDetailBuilder.addPropertyValue("name", classMethodName);
        jobDetailBuilder.addPropertyValue("group", jobAnnotation.group());

        acf.registerBeanDefinition(jobBeanName, jobDetailBuilder.getBeanDefinition());

        Object jobDetail = acf.getBean(jobBeanName);

        Pattern p = Pattern.compile("\\$\\{(.+)\\}");
        Matcher m = p.matcher(jobAnnotation.cron());
        String cron = "";
        if (m.find()) {
            cron = SpringPropUtils.get(m.group(1).trim());
        } else {
            cron = jobAnnotation.cron();
        }
        if (StringUtils.isEmpty(cron)) {
            logger.info(classMethodName + " cron not found ! please check !");
            return;
        }

        BeanDefinitionBuilder cronTriggerBuilder = BeanDefinitionBuilder.genericBeanDefinition(CronTriggerFactoryBean.class);
        cronTriggerBuilder.addPropertyValue("jobDetail", jobDetail);
        cronTriggerBuilder.addPropertyValue("cronExpression", cron);
        cronTriggerBuilder.addPropertyValue("group", jobAnnotation.group());
        String cronTriggerBeanName = classMethodName + "CronTrigger";
        acf.registerBeanDefinition(cronTriggerBeanName, cronTriggerBuilder.getBeanDefinition());

        this.triggersList.add((Trigger) acf.getBean(cronTriggerBeanName));
    }

}
