package random;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by wxf on 14-5-5.
 */
public class RandomUtil {
    public static <T> WeightMeta<T> buildWeightMeta(final Map<T, Integer> weightMap) {
        final int size = weightMap.size();
        Object[] nodes = new Object[size];
        int[] weights = new int[size];
        int index = 0;
        int weightAdder = 0;
        for (Map.Entry<T, Integer> each : weightMap.entrySet()) {
            nodes[index] = each.getKey();
            weights[index++] = (weightAdder = weightAdder + each.getValue());
        }
        return new WeightMeta<T>((T[]) nodes, weights);
    }
}
