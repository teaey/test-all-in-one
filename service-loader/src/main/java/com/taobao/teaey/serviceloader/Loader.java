package com.taobao.teaey.serviceloader;

import java.util.ServiceLoader;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public class Loader {
    public static void main(String[] args) {
        Handler handler = ServiceLoader.load(Handler.class, Loader.class.getClassLoader()).iterator().next();
        System.out.println(handler);
        Dispatcher dispatcher = ServiceLoader.load(Dispatcher.class, Loader.class.getClassLoader()).iterator().next();
        System.out.println(dispatcher);
    }
}
