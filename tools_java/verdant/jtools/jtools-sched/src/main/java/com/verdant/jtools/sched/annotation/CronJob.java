package com.verdant.jtools.sched.annotation;


import org.quartz.Scheduler;

import java.lang.annotation.*;

/**
 * 定时任务注释类
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CronJob {
    //cron表达式 配置于文件中或直接写
    String cron() default "";

    //分组名称：用户Service的定时 或其他Service的定时部署在不同容器中
    String group() default Scheduler.DEFAULT_GROUP;
}
