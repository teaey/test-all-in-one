package network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiaofei.wxf
 */
public class OIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动，监听端口：" + 8888);
        while (true) {
            try {
                Socket s = serverSocket.accept();
                s.setTcpNoDelay(true);
                //new Thread(new Reader(s)).start();
                System.out.println("新连接");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }


    static class Reader implements Runnable {
        private Socket socket;

        Reader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    Thread.sleep(10000);
                    socket.close();
                    int data = socket.getInputStream().read();
                    if (-1 == data) {
                        socket.close();
                        System.out.println("断开连接:" + socket);
                        return;
                    }
                    socket.getOutputStream().write(data);
                    socket.getOutputStream().flush();
                    System.out.println("Read:" + data + " Write:" + (data));
                } catch (Throwable e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("断开连接:" + socket);
                    return;
                }
            }
        }
    }
}
