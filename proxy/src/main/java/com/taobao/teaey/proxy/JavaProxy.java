package com.taobao.teaey.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

/**
 * @author xiaofei.wxf
 * @date 13-12-31
 */
public class JavaProxy {
    public static void main(String[] args) {
        int[] array = new int[] {5, 2, 6, 3, 9, 3, 5, 7, 8,};
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }

    public <T> T getProxy(Class<T> inter) {
        T t = (T) java.lang.reflect.Proxy
            .newProxyInstance(JavaProxy.class.getClassLoader(), new Class[] {inter},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                        System.out.println("java proxy invoke");
                        return new Random().nextInt();
                    }
                });
        return t;
    }
}
