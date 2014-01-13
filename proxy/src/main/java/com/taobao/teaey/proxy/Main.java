package com.taobao.teaey.proxy;

/**
 * @author xiaofei.wxf
 * @date 13-12-31
 */
public class Main {
    public static void main(String[] args) {
        //new JavaProxy().getProxy(SimpleService.class).random();
        new JavaProxy().getProxy(SimpleService.class).random(10);
        new JavassistProxy().getProxy(SimpleService.class);
    }
}
