package heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xiaofei.wxf
 * @date 13-12-25
 */
public class MemoryMapReader {

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args)
        throws FileNotFoundException, IOException, InterruptedException {


        FileChannel fc = new RandomAccessFile(new File("D:/mapped.txt"), "rw").getChannel();

        long bufferSize = 8 * 1000;
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, bufferSize);
        long oldSize = fc.size();

        long currentPos = 0;
        long xx = currentPos;


        long startTime = System.currentTimeMillis();
        long lastValue = -1;
        for (; ; ) {

            while (mem.hasRemaining()) {
                lastValue = mem.getLong();
                currentPos += 8;
            }
            if (currentPos < oldSize) {
                xx = xx + mem.position();
                mem = fc.map(FileChannel.MapMode.READ_ONLY, xx, bufferSize);
                continue;
            } else {
                long end = System.currentTimeMillis();
                long tot = end - startTime;
                System.out
                    .println(String.format("Last Value Read %s , Time(ms) %s ", lastValue, tot));
                System.out.println("Waiting for message");
                while (true) {
                    long newSize = fc.size();
                    if (newSize > oldSize) {
                        oldSize = newSize;
                        xx = xx + mem.position();
                        mem = fc.map(FileChannel.MapMode.READ_ONLY, xx, oldSize - xx);
                        System.out.println("Got some data");
                        break;
                    }
                }
            }


        }

    }

}

