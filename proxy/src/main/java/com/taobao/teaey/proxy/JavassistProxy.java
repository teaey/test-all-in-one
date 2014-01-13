package com.taobao.teaey.proxy;

import java.security.ProtectionDomain;

/**
 * @author xiaofei.wxf
 * @date 13-12-31
 */
public class JavassistProxy {
    public <T> T getProxy(Class<T> inter) {
        ProtectionDomain domain = inter.getProtectionDomain();
        System.out.println(domain);
        return null;
    }
}
