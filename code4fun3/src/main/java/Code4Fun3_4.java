import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class Code4Fun3_4 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new HashMap<String, Integer>(50000);
        FileInputStream fis = new FileInputStream("C:/Users/xiaofei.wxf.HZ/Desktop/doc.txt");
        System.out.println("file bytes:" + fis.available());
        final List<Thread> threads = new ArrayList<Thread>();
        //int charBytes = 0;
        int readLength = -1;
        final byte[] buffer = new byte[102400 << 4];
        StringBuilder sb = new StringBuilder(8);
        while ((readLength = fis.read(buffer)) > -1) {
            final ByteBuffer tmp = ByteBuffer.allocate(16);
            byte last = buffer[buffer.length - 1];
            if (!split(last)) {
                tmp.put(last);
                last = (byte) fis.read();
            }
            Thread t = new Thread() {
                public void run() {
                    //charBytes += tmp.position();
                    for (byte readed : buffer) {
                        //FIXME:优化
                        if (!(readed == empty || readed == dian || readed == enter || readed == yinhang || readed == douhao || readed == danyinhao || readed == langhao || readed == wenhao || readed == tanhao || readed == jianhao || readed == fenhao)) {
                            //charBytes++;
                        }
                        //new Object();
                    }
                    if (tmp.position() > 0) {
                        tmp.rewind();
                        for (int i = 0; i < tmp.limit(); i++) {
                            tmp.get();
                        }
                    }
                }
            };
            t.start();
            threads.add(t);
        }
        fis.close();
        for (Thread each : threads) {
            try {
                each.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        Helper.top10(map);
        System.out.println((end - start) + " ms");
    }

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
    static final byte[] split = new byte[]{dian, douhao, jianhao, langhao, yinhang, wenhao, danyinhao, tanhao, fenhao, empty, enter};
    static final String[] ingoreArray = new String[]{"the", "and", "i", "to", "of", "a", "in", "was", "that", "had", "he", "you", "his", "my", "it", "as", "with", "her", "for", "on"};
    static final HashSet<String> ignoreSet = new HashSet<String>(Arrays.asList(ingoreArray));

    static final boolean ignore(String word) {
        for (String each : ingoreArray) {
            if (each.equals(word)) {
                return true;
            }
        }
        return false;
    }

    static final boolean split(byte readed) {
        return readed == empty || readed == dian || readed == enter || readed == yinhang || readed == douhao || readed == danyinhao || readed == langhao || readed == wenhao || readed == tanhao || readed == jianhao || readed == fenhao;
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
