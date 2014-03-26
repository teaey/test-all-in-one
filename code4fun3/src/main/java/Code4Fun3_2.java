import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Code4Fun3_2 {
    static final int dian = '.';
    static final int douhao = ',';
    static final int jianhao = '-';
    static final int langhao = '~';
    static final int yinhang = '"';
    static final int wenhao = '?';
    static final int danyinhao = '\'';
    static final int tanhao = '!';
    static final int fenhao = ';';
    static final int empty = ' ';
    static final int enter = '\n';
    static final String[] ingoreArray =
        new String[] {"the", "and", "i", "to", "of", "a", "in", "was", "that", "had", "he", "you",
            "his", "my", "it", "as", "with", "her", "for", "on"};
    static final HashSet<String> ignoreSet = new HashSet<String>(Arrays.asList(ingoreArray));

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new HashMap<String, Integer>(50000);
        File file = new File("C:/Users/xiaofei.wxf.HZ/Desktop/document.txt");
        FileInputStream fis = new FileInputStream(file);
        System.out.println(fis.available());
        System.out.println(file.length());
        FileChannel channel = fis.getChannel();
        MappedByteBuffer mmap = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        byte[] all = new byte[(int) file.length()];
        mmap.get(all);
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < all.length; i++) {
            byte data = all[i];
            if (split(data)) {
                if (sb.length() > 0) {
                    String key = sb.toString();
                    //if (!ignoreSet.contains(key)) {
                    //increment(map, key);
                    sb = new StringBuilder(8);
                    //}
                }
            } else {
                sb.append(toLowcase(data));
            }
        }
        fis.close();
        print(map);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }

    static final boolean ignore(String word) {
        for (String each : ingoreArray) {
            if (each.equals(word)) {
                return true;
            }
        }
        return false;
    }

    static final boolean split(byte readed) {
        return readed == enter || readed == empty || readed == douhao || readed == danyinhao
            || readed == yinhang || readed == langhao || readed == wenhao || readed == tanhao
            || readed == jianhao || readed == fenhao || readed == dian;
    }

    static final boolean split(char readed) {
        return readed == enter || readed == empty || readed == douhao || readed == danyinhao
            || readed == yinhang || readed == langhao || readed == wenhao || readed == tanhao
            || readed == jianhao || readed == fenhao || readed == dian;
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
        System.out.println(map.size());
        //Set<Map.Entry<String, Integer>> entities = map.entrySet();
        //        for (Map.Entry<String, Integer> each : entities) {
        //            System.out.println(each.getKey() + ":" + each.getValue());
        //        }
    }

    static final char toLowcase(byte readed) {
        return (char) ((readed >= 65 && readed <= 90) ? (readed + 32) : readed);
    }

    static final char toLowcase(char readed) {
        return (char) ((readed >= 65 && readed <= 90) ? (readed + 32) : readed);
    }


    static class Counter implements Runnable {
        Map<String, Integer> map = new HashMap<String, Integer>(50000);

        @Override
        public void run() {

        }

    }
}
