package test;

import com.codahale.metrics.*;
import org.junit.Test;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.*;

/**
 * Created by XiaoFei on 2014/8/27.
 */
public class MetricsTest {
    final MetricRegistry metrics = new MetricRegistry();
    final String         nameKey = "metricstest";

    @Test
    public void testGauges() {
        final Queue queue = new LinkedBlockingQueue();
        Gauge gauge = metrics.register(MetricRegistry.name(MetricsTest.class, nameKey
                        , "size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return queue.size();
                    }
                });
        queue.add(1);
        assert gauge.getValue().equals(1);
        queue.add(1);
        assert gauge.getValue().equals(2);
    }

    @Test
    public void testRatioGauge() throws InterruptedException {

        RatioGauge ratioGauge = metrics.register(MetricRegistry.name(MetricsTest.class, nameKey
                , "size"), new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(1, System.currentTimeMillis());
            }
        });

        System.out.println(ratioGauge.getValue());
        Thread.sleep(1000);
        System.out.println(ratioGauge.getValue());
    }

    @Test
    public void testCachedGauge() throws InterruptedException {
        CachedGauge cachedGauge = metrics.register(name(MetricsTest.class, nameKey, "size"),
                new CachedGauge<Long>(1, TimeUnit.SECONDS) {
                    @Override
                    protected Long loadValue() {
                        // assume this does something which takes a long time
                        return System.currentTimeMillis();
                    }
                });
        for(int i=0;i<10;i++) {
            System.out.println(cachedGauge.getValue());
            Thread.sleep(1000);
        }
    }

    @Test
    public void testJmx() throws MalformedObjectNameException, InterruptedException {
        //"net.sf.ehcache:type=Cache,scope=sessions,name=eviction-count"
        metrics.register(name(MetricsTest.class, "cache-evictions"),
                new JmxAttributeGauge(new ObjectName("net.sf.ehcache:type=Cache,scope=sessions,name=eviction-count"), "Value"));

        Thread.sleep(10000000);

    }
}
