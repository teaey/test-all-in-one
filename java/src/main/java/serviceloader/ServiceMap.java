package serviceloader;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class ServiceMap {
    public static void main(String[] args) {
        Iterator<TestService> s =
            ServiceLoader.load(TestService.class, ServiceMap.class.getClassLoader()).iterator();
        while (s.hasNext()) {
            s.next().printV();
        }
    }
}
