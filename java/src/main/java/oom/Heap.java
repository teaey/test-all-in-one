package oom;

/**
 * @author xiaofei.wxf
 */
public class Heap {
    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override public void run() {
                try{
                    byte[] bytes = new byte[1024 * 1024 * 256 * 2 * 2];
                    System.out.println(bytes.length);
                }catch (Exception e){
                    //e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(1000000);
    }
}
