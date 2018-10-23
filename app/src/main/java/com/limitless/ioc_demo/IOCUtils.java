package com.limitless.ioc_demo;

import android.util.Log;
import android.view.View;

import com.limitless.ioc_demo.annot.ContentView;
import com.limitless.ioc_demo.annot.EventBase;
import com.limitless.ioc_demo.annot.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Nick on 2018/10/23
 *
 * @author Nick
 */
public class IOCUtils {

    private static final String TAG = "IOCUtils";

    public static void inject(Object context) {
        Class<?> aClass = context.getClass();
        layoutInject(context, aClass);

        viewInject(context, aClass);

        eventInject(context, aClass);
    }

    /**
     * 事件的注入
     */
    private static void eventInject(Object context, Class<?> aClass) {

        //获取类中所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method method : declaredMethods) {

            //获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {

                //获取注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();

                //通过注解的类型，获取自定义注解的注解
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);

                if (eventBase != null) {

                    // 注解中配置的事件名
                    String listenerSetter = eventBase.listenerSetter();

                    // 注解中配置的事件类
                    Class<?> listenerType = eventBase.listenerType();

                    try {
                        //获取方法注解上控件的ID值
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds= (int[]) valueMethod.invoke(annotation);

                        for (int id : viewIds) {

                            //通过findViewById来查找控件
                            Method findViewById = aClass.getMethod("findViewById", int.class);
                            View view = (View) findViewById.invoke(context, id);

                            if (view != null) {

                                //查找控件里的事件方法
                                Method listenerMethod = view.getClass().getMethod(listenerSetter, listenerType);

                                //通过代理设置回调
                                ListenerInvocationHandler handler = new ListenerInvocationHandler(context, method);
                                Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                                //调用控件里的事件方法
                                listenerMethod.invoke(view, proxy);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "eventInject: ---"+e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 控件的注入
     */
    private static void viewInject(Object context, Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (annotation != null) {
                int viewID = annotation.value();
                try {
                    Method findViewById = aClass.getMethod("findViewById", int.class);
                    Object invoke = findViewById.invoke(context, viewID);
                    field.setAccessible(true);
                    field.set(context, invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "viewInject: ---"+e.getMessage());
                }
            }
        }
    }


    /**
     * 布局的注入
     */
    private static void layoutInject(Object context, Class<?> aClass) {

        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if (annotation != null) {
            int layoutID = annotation.value();
            try {
                Method setContentViewMethod = aClass.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(context, layoutID);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "layoutInject: ---"+e.getMessage());
            }
        }
    }

}
