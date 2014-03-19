package serviceloader;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class TestServiceImpl implements TestService {
    @Override
    public void printV() {
        System.out.println("impl v1");
    }
}
