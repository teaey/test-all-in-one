import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Code4Fun3_3 {
    static class Counter implements Runnable {
        final Map<String, Integer> map = new HashMap<String, Integer>(50000);
        final FileInputStream fis;
        final int offset;
        final int totalNum;

        Counter(FileInputStream fis, int offset, int totalNum) {
            this.fis = fis;
            this.offset = offset;
            this.totalNum = totalNum;
        }

        @Override
        public void run() {
            try {
                fis.skip(offset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int handleNum = 0;
            int readLength = -1;
            byte[] buffer = new byte[4096];
            StringBuilder sb = new StringBuilder(8);
            try {
                while ((readLength = fis.read(buffer)) > -1) {
                    int tohandle = readLength + handleNum > totalNum ? (totalNum - handleNum) : readLength;
                    for (int i = 0; i < tohandle; i++) {
                        byte readed = buffer[i];
                        if (readed == empty || readed == dian || readed == enter || readed == yinhang || readed == douhao || readed == danyinhao || readed == langhao || readed == wenhao || readed == tanhao || readed == jianhao || readed == fenhao) {
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
                print();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final void print() {
            System.out.println(map.size());
            //Set<Map.Entry<String, Integer>> entities = map.entrySet();
//        for (Map.Entry<String, Integer> each : entities) {
//            System.out.println(each.getKey() + ":" + each.getValue());
//        }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int threadNum = Runtime.getRuntime().availableProcessors();
        System.out.println(threadNum);
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream("C:/Users/xiaofei.wxf.HZ/Desktop/doc.txt");
        int avalable = fis.available();
        System.out.println("file size:" + avalable);
        int averageBytes = avalable / threadNum;
        Thread[] threadArray = new Thread[threadNum];
        int index = 0;
        for (int i = 0; i < threadNum; i++) {
            int offset = index;
            int aval = offset + averageBytes > avalable ? avalable - offset : averageBytes;
            index += aval;
            FileInputStream inputStream = null;
            if (i == 0) {
                inputStream = fis;
            } else {
                inputStream = new FileInputStream("C:/Users/xiaofei.wxf.HZ/Desktop/doc.txt");
            }
            Counter counter = new Counter(inputStream, offset, aval);
            Thread t = new Thread(counter);
            t.start();
            threadArray[i] = t;
        }
        for (Thread each : threadArray) {
            each.join();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " ms");
    }

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
        return readed == enter || readed == empty || readed == douhao || readed == danyinhao || readed == yinhang || readed == langhao || readed == wenhao || readed == tanhao || readed == jianhao || readed == fenhao || readed == dian;
    }

    static final boolean split(char readed) {
        return readed == enter || readed == empty || readed == douhao || readed == danyinhao || readed == yinhang || readed == langhao || readed == wenhao || readed == tanhao || readed == jianhao || readed == fenhao || readed == dian;
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
}
