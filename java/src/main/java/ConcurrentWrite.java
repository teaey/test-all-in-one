import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 对Socket的写不是线程安全的，所以必须在实现中同步
 */
public class ConcurrentWrite {
    public static void main(String[] args) {
        server("127.0.0.1", 8888);
        client("127.0.0.1", 8888);
    }

    static void server(String host, int port) {
        try {
            final ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        int read = 0;
                        int except = 0;
                        while ((read = clientSocket.getInputStream().read()) != -1) {
                            if (read == except) {
                                except = (except == 0) ? 1 : 0;
                            } else {
                                throw new RuntimeException("except=" + except + " read=" + read);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(false);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void client(String host, int port) {
        final Socket client = new Socket();
        try {
            client.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        try {
                            client.getOutputStream().write(i);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                        i = (i == 0) ? 1 : 0;
                    }
                }
            });
            t.setDaemon(false);
            t.start();
        }
    }
}
