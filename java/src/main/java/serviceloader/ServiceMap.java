package serviceloader;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class ServiceMap {
    private static final Map<Class<?>, Object> Cache = new ConcurrentHashMap<Class<?>, Object>();
    public static <T> T get(Class<T> clazz) {
        T ret = (T) Cache.get(clazz);

        if(null != ret){
            return ret;
        }

        Iterator<T> s = ServiceLoader.load(clazz, ServiceMap.class.getClassLoader()).iterator();
        if (s.hasNext()) {
            ret = s.next();
        }
        return ret;
    }

    public static void main(String[] args) {
        ServiceA a = get(ServiceA.class);
        ServiceB b = get(ServiceB.class);
        System.out.println(a.getB() == b);
        System.out.println(b.getA() == a);

        a.printA();
        b.printB();


    }
}
