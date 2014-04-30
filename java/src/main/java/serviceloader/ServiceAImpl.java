package serviceloader;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class ServiceAImpl implements ServiceA {
    private ServiceB serviceB = ServiceMap.get(ServiceB.class);
    @Override
    public void printA() {
        System.out.println("A impl");
    }

    @Override public ServiceB getB() {
        return serviceB;
    }
}
