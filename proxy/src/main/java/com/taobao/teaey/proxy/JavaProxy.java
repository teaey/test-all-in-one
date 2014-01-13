package com.taobao.teaey.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * @author xiaofei.wxf
 * @date 13-12-31
 */
public class JavaProxy{
    public <T> T getProxy(Class<T> inter) {
        T t = (T) java.lang.reflect.Proxy.newProxyInstance(JavaProxy.class.getClassLoader(), new Class[]{inter}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("java proxy invoke");
                return new Random().nextInt();
            }
        });
        return t;
    }
}
