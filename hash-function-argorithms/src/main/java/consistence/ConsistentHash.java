package consistence;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>Company: www.taobao.com</p>
 *
 * @author wxf
 * @date 14-7-21
 */
public class ConsistentHash<T> {

    private static final int DEFAULT_NUMBEROFREPLICAS = 10;
    private static final HashFunction DEFAULT_HASHFUNCTION = new JdkHashFunction();

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistentHash(Collection<T> nodes) {
        this(DEFAULT_HASHFUNCTION, DEFAULT_NUMBEROFREPLICAS, nodes);
    }

    public ConsistentHash(HashFunction hashFunction,
                          Collection<T> nodes) {
        this(hashFunction, DEFAULT_NUMBEROFREPLICAS, nodes);
    }

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
                          Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            if (null == node) {
                continue;
            }
            add(node);
        }
    }

    /**
     * 添加一个真实的节点
     * 一个真实节点会映射出{@link #numberOfReplicas} 个虚拟节点
     *
     * @param node
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hash(node, i), node);
        }
    }

    /**
     * 移除真是的节点
     * 实际上移除了{@link #numberOfReplicas} 个虚拟节点
     *
     * @param node
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hash(node, i));
        }
    }

    private int hash(T node, int index) {
        String key = node.toString() + "#" + index;
        return hashFunction.hash(DigestUtils.md5(key));
    }

    /**
     * 获取键值对应的虚拟节点
     *
     * @param key
     * @return
     */
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
