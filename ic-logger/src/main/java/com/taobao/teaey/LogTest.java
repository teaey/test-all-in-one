package com.taobao.teaey;

import org.slf4j.LoggerFactory;

/**
 * @author xiaofei.wxf
 * @date 13-12-23
 */
public class LogTest {
    public static void main(String[] args) throws InterruptedException {
        LoggerFactory.getLogger(LogTest.class).trace("你好这是个测试代码");
        LoggerFactory.getLogger(LogTest.class).debug("你好这是个测试代码");
        LoggerFactory.getLogger(LogTest.class).info("你好这是个测试代码");
        LoggerFactory.getLogger(LogTest.class).warn("你好这是个测试代码");
        LoggerFactory.getLogger(LogTest.class).error("你好这是个测试代码");
//        Thread.sleep(3000);
//        LoggerFactory.getLogger("LOGGER").info("你好这是个测试代码");
////        Thread.sleep(3000);
//        LoggerFactory.getLogger("LOGGER").info("你好这是个测试代码");
    }
}
