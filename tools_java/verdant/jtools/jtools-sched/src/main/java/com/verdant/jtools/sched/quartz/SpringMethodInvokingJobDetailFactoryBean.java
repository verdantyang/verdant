package com.verdant.jtools.sched.quartz;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.*;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;

/**
 * 定时任务（和MethodInvoker是依赖关系）
 * 参考org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean（继承自MethodInvoker，只能包装一个Job）
 */
public class SpringMethodInvokingJobDetailFactoryBean
        implements FactoryBean<JobDetail>, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean {
    private String name;
    private String group = Scheduler.DEFAULT_GROUP;
    private boolean concurrent = true;
    private boolean durability = true;
    private boolean shouldRecover = true;
    private String targetBeanName;
    private String targetMethod;
    private String beanName;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private static BeanFactory beanFactory;
    private JobDetail jobDetail;

    /**
     * Set the name of the job.
     * <p>Default is the bean name of this FactoryBean.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the group of the job.
     * <p>Default is the default group of the Scheduler.
     *
     * @see org.quartz.Scheduler#DEFAULT_GROUP
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Specify whether or not multiple jobs should be run in a concurrent fashion.
     * The behavior when one does not want concurrent jobs to be executed is
     * realized through adding the {@code @PersistJobDataAfterExecution} and
     * {@code @DisallowConcurrentExecution} markers.
     * More information on stateful versus stateless jobs can be found
     * <a href="http://www.quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/tutorial-lesson-03">here</a>.
     * <p>The default setting is to run jobs concurrently.
     */
    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public void setDurability(boolean durability) {
        this.durability = durability;
    }

    public void setShouldRecover(boolean shouldRecover) {
        this.shouldRecover = shouldRecover;
    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {

        // Use specific name if given, else fall back to bean name.
        String name = (this.name != null ? this.name : this.beanName);

        // Consider the concurrent flag to choose between stateful and stateless job.
        Class jobClass = (this.concurrent ? MethodInvokingJob.class : StatefulMethodInvokingJob.class);

        // Build JobDetail instance.
        JobDetailImpl jdi = new JobDetailImpl();
        jdi.setName(name);
        jdi.setGroup(this.group);
        jdi.setJobClass(jobClass);
        jdi.setDurability(true);
        jdi.setRequestsRecovery(true);
        jdi.getJobDataMap().put("targetBeanName", targetBeanName);
        jdi.getJobDataMap().put("targetMethod", targetMethod);
        this.jobDetail = jdi;
    }

    @Override
    public JobDetail getObject() {
        return this.jobDetail;
    }

    @Override
    public Class<? extends JobDetail> getObjectType() {
        return (this.jobDetail != null ? this.jobDetail.getClass() : JobDetail.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    /**
     * Quartz Job implementation that invokes a specified method.
     * Automatically applied by MethodInvokingJobDetailFactoryBean.
     */
    public static class MethodInvokingJob implements Job {

        protected static final Logger logger = LoggerFactory.getLogger(SpringMethodInvokingJobDetailFactoryBean.MethodInvokingJob.class);
        private MethodInvoker methodInvoker;

        public MethodInvokingJob() {
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try {
                String targetBeanName = context.getMergedJobDataMap().getString("targetBeanName");
                String targetMethod = context.getMergedJobDataMap().getString("targetMethod");
                Object targetBean = beanFactory.getBean(targetBeanName);
                if (targetBeanName == null || targetBean == null)
                    throw new JobExecutionException("targetBean cannot be null.", false);
                if (targetMethod == null)
                    throw new JobExecutionException("targetMethod cannot be null.", false);

                MethodInvoker beanMethod = new MethodInvoker();
                beanMethod.setTargetObject(targetBean);
                beanMethod.setTargetMethod(targetMethod);
                beanMethod.setArguments(new Object[0]);
                beanMethod.prepare();
                logger.info("Schedule Invoking Bean: " + targetBean + "; Method: " + targetMethod + ".");
                context.setResult(beanMethod.invoke());
            } catch (JobExecutionException e) {
                throw e;
            } catch (Exception e) {
                throw new JobExecutionException(e);
            }
        }
    }

    /**
     * Extension of the MethodInvokingJob, implementing the StatefulJob interface.
     * Quartz checks whether or not jobs are stateful and if so,
     * won't let jobs interfere with each other.
     */
    @PersistJobDataAfterExecution
    @DisallowConcurrentExecution
    public static class StatefulMethodInvokingJob extends SpringMethodInvokingJobDetailFactoryBean.MethodInvokingJob {
        public StatefulMethodInvokingJob() {
        }
    }

}