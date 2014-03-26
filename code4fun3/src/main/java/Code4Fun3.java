import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Code4Fun3 {
    static final byte dian = '.';
    static final byte douhao = ',';
    static final byte jianhao = '-';
    static final byte langhao = '~';
    static final byte yinhang = '"';
    static final byte wenhao = '?';
    static final byte danyinhao = '\'';
    static final byte tanhao = '!';
    static final byte fenhao = ';';
    static final byte empty = ' ';
    static final byte enter = '\n';
    static final byte[] split =
        new byte[] {dian, douhao, jianhao, langhao, yinhang, wenhao, danyinhao, tanhao, fenhao,
            empty, enter};
    static final String[] ingoreArray =
        new String[] {"the", "and", "i", "to", "of", "a", "in", "was", "that", "had", "he", "you",
            "his", "my", "it", "as", "with", "her", "for", "on"};
    static final HashSet<String> ignoreSet = new HashSet<String>(Arrays.asList(ingoreArray));

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new HashMap<String, Integer>(50000);
        FileInputStream fis = new FileInputStream("C:/Users/xiaofei.wxf.HZ/Desktop/document.txt");
        System.out.println("file size:" + fis.available());
        int readLength = -1;
        byte[] buffer = new byte[fis.available() / 10];
        StringBuilder sb = new StringBuilder(8);
        while ((readLength = fis.read(buffer)) > -1) {
            for (byte readed : buffer) {
                if (!isSplit(readed)) {
                    //if (split(readed)) {
                    if (sb.length() > 0) {
                        String key = sb.toString();
                        //if (!ignoreSet.contains(key)) {
                        //increment(map, key);
                        Integer value = map.get(key);
                        if (null == value) {
                            map.put(key, 1);
                        } else {
                            map.put(key, ++value);
                        }
                        sb = new StringBuilder(8);
                        //}
                    }
                } else {
                    sb.append((char) ((readed >= 65 && readed <= 90) ? (readed + 32) : readed));
                }
            }
        }
        fis.close();
        long end = System.currentTimeMillis();
        Helper.top10(map);
        System.out.println((end - start) + " ms");
    }

    static final boolean ignore(String word) {
        for (String each : ingoreArray) {
            if (each.equals(word)) {
                return true;
            }
        }
        return false;
    }

    static final boolean isSplit(byte readed) {
        if ((readed >= 97 && readed <= 122) || (readed >= 65 && readed <= 90)) {
            return false;
        } else {
            return true;
        }
    }

    static final boolean split1(byte readed) {
        for (byte each : split) {
            if (each == readed) {
                return true;
            }
        }
        return false;
    }

    static final void increment(Map<String, Integer> map, String key) {
        Integer value = map.get(key);
        if (null == value) {
            map.put(key, 1);
        } else {
            map.put(key, ++value);
        }
    }

    static final void print(Map<String, Integer> map) {
        System.out.println("map size:" + map.size());
        final Set<Map.Entry<String, Integer>> entities = map.entrySet();
        //        for (Map.Entry<String, Integer> each : entities) {
        //            System.out.println(each.getKey() + ":" + each.getValue());
        //        }
    }

    static final char toLowcase(byte readed) {
        return (char) ((readed >= 65 && readed <= 90) ? (readed + 32) : readed);
    }

}
