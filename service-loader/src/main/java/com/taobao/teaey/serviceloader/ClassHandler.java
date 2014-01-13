package com.taobao.teaey.serviceloader;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public class ClassHandler implements Handler {
    @Override
    public void handle() {
        System.out.println(ClassHandler.class);
    }
}
