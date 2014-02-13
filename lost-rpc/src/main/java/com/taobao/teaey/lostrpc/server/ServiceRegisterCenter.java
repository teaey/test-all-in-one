package com.taobao.teaey.lostrpc.server;

import com.google.protobuf.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class ServiceRegisterCenter {
    private static final ConcurrentHashMap<String, Service> CACHE = new ConcurrentHashMap<String, Service>(1 << 10);

    public static Service addService(Service service) {
        if (null == service) {
            throw new NullPointerException("service");
        }
        return CACHE.put(service.getDescriptorForType().getFullName(), service);
    }

    public static Service removeService(Service service) {
        return CACHE.remove(service);
    }

    public static Service getService(String fullname) {
        return CACHE.get(fullname);
    }
}
