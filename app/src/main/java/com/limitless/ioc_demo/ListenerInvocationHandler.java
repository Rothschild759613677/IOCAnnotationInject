package com.limitless.ioc_demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * 事件的回调
 * Created by Nick on 2018/10/23
 *
 * @author Nick
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Object activity;

    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method method) {
        this.activity = activity;
        this.activityMethod = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return activityMethod.invoke(activity,args);
    }
}
