package com.taobao.teaey.dns;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author xiaofei.wxf
 * @date 13-12-18
 */
public class DNSCacheTest {
    /**
     * 1. 在首次调用InetAddress.getByName()前，设置java.security.Security.setProperty("networkaddress.cache.ttl", "0");
     * 2. 修改jre/lib/security/java.security 下的 networkaddress.cache.ttl 属性
     * 3. jvm启动参数中设置-Dsun.net.inetaddr.ttl=0
     * 4. 调用clearCache方法清除
     *
     * @param args
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, NoSuchFieldException, IllegalAccessException {
        java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        InetAddress addr1 = InetAddress.getByName("www.baidu.com");
        System.out.println(addr1.getHostAddress());
        //clearCache();
        //在下一行设置断点.
        //放在此处无效，可能是因为已经缓存了cache
        //java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        InetAddress addr2 = InetAddress.getByName("www.baidu.com");
        System.out.println(addr2.getHostAddress());
        InetAddress addr3 = InetAddress.getByName("www.google.com");
        System.out.println(addr3.getHostAddress());
        InetAddress addr4 = InetAddress.getByName("www.google.com");
        System.out.println(addr4.getHostAddress());
        //clearCache();
    }

    public static void clearCache() throws NoSuchFieldException, IllegalAccessException {
        //修改缓存数据开始
        Class clazz = InetAddress.class;
        final Field cacheField = clazz.getDeclaredField("addressCache");
        cacheField.setAccessible(true);
        final Object obj = cacheField.get(clazz);
        Class cacheClazz = obj.getClass();
        final Field cachePolicyField = cacheClazz.getDeclaredField("type");
        final Field cacheMapField = cacheClazz.getDeclaredField("cache");
        cachePolicyField.setAccessible(true);
        cacheMapField.setAccessible(true);
        final Map cacheMap = (Map)cacheMapField.get(obj);
        System.out.println(cacheMap);
        cacheMap.remove("www.baidu.com");
    }
}
