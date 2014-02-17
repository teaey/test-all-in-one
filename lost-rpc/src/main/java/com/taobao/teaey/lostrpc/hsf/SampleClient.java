package com.taobao.teaey.lostrpc.hsf;

import com.taobao.hsf.standalone.HSFEasyStarter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public class SampleClient {
    public static void main(String[] args) {
        HSFEasyStarter.start("D:/hsf/", "2.0.1.4");
        String springResourcePath = "spring-hsf-consumer.xml";
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(springResourcePath);
        LoginService loginService = (LoginService) ctx.getBean("loginServiceConsumer");
        Date d = new Date();
        loginService.unreturn(null);
        System.out.println(loginService.withreturn(d) == d);
        System.out.println(loginService.withreturn(d).equals(d));
    }
}
