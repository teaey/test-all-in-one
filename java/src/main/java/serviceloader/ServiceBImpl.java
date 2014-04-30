package serviceloader;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class ServiceBImpl implements ServiceB {
    private ServiceA serviceA = ServiceMap.get(ServiceA.class);
    @Override
    public void printB() {
        System.out.println("B impl");
    }

    @Override public ServiceA getA() {
        return serviceA;
    }
}
