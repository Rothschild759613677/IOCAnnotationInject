package com.limitless.ioc_demo.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 在注解上使用的注解
 * Created by Nick on 2018/10/23
 *
 * @author Nick
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    /**
     * 事件源
     */
    String listenerSetter();

    /**
     * 事件监听的类型
     */
    Class<?> listenerType();

}
