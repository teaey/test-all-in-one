package com.taobao.teaey.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaofei.wxf
 * @date 13-12-31
 */
public class Main {
    static interface TestInterface {
        void test();
    }

    static class TestInvocationHandler implements InvocationHandler {
        static int i = 0;
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            i++;
            return null;
        }
    }

    public static void main(String[] args) {
        InvocationHandler handler = new TestInvocationHandler();
        final TestInterface ti1 = (TestInterface) JavassistProxy.getProxy(TestInterface.class)
                .newInstance(handler);

        final TestInterface ti2 = (TestInterface) Proxy.newProxyInstance(TestInterface.class.getClassLoader(), new Class[]{TestInterface.class}, handler);

        final int round = 1000000000;

        for (int i = 0; i < round; i++) {
            ti1.test();
            ti2.test();
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                long t1 = System.nanoTime();
//                for (int i = 0; i < round; i++) {
//                    ti1.test();
//                }
//                long t2 = System.nanoTime();
//                System.out.println("Javassist:" + TimeUnit.NANOSECONDS.toMillis(t2 - t1));
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                long t1 = System.nanoTime();
//                for (int i = 0; i < round; i++) {
//                    ti2.test();
//                }
//                long t2 = System.nanoTime();
//                System.out.println("Jdk:" + TimeUnit.NANOSECONDS.toMillis(t2 - t1));
//            }
//        }).start();


        long t1 = System.nanoTime();
        for (int i = 0; i < round; i++) {
            ti2.test();
        }
        long t2 = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(t2 - t1));

        long t3 = System.nanoTime();
        for (int i = 0; i < round; i++) {
            ti1.test();
        }
        long t4 = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(t4 - t3));

    }
}
