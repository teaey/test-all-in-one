package random;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxf on 14-5-5.
 */
public class RandomUtilTest {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i);
        }

        WeightMeta<Integer> rm = RandomUtil.buildWeightMeta(map);
        System.out.println(rm);

        for (int i = 0; i < 45; i++) {
            System.out.println("random=" + i + "\tnode=" + rm.random(i));
        }
    }
}
