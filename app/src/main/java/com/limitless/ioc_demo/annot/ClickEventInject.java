package com.limitless.ioc_demo.annot;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件的注入
 * Created by Nick on 2018/10/23
 *
 * @author Nick
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class)
public @interface ClickEventInject {

    int[] value() default -1;

}
