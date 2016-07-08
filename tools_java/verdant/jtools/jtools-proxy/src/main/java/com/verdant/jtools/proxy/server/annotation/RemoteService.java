package com.verdant.jtools.proxy.server.annotation;

import com.commons.proxy.center.transfer.model.TransferType;
import com.commons.proxy.serializable.SerializerType;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态代理注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface RemoteService {

    String name() default "";

    //接受参数序列化方式
    SerializerType serialize() default SerializerType.KRYO;

    //接受传输方法
    TransferType transfer() default TransferType.HTTP;

    float version() default 1.0f;

    boolean enable() default true;

    Class<?> serviceInterface();
}
