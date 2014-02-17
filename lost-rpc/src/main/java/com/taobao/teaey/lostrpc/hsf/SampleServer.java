package com.taobao.teaey.lostrpc.hsf;

import com.taobao.hsf.standalone.HSFEasyStarter;
import com.taobao.hsf.util.RequestCtxUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public class SampleServer {
    public static void main(String[] args) throws InterruptedException {
        HSFEasyStarter.start("d:/hsf/", "2.0.1.4");
        String springResourcePath = "spring-hsf-provider.xml";
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(springResourcePath);

        Thread.sleep(30 * 1000);

        String ip = RequestCtxUtil.getClientIp();
        System.out.println(ip);
    }
}
