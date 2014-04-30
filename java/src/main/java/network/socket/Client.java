package network.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author xiaofei.wxf
 */
public interface Client<T extends Client> {
    T connect(String host, int port) throws IOException;

    T connect(InetSocketAddress addr) throws IOException;

    T shutdown();

    T ask(Object req) throws IOException;

    T answer(Object resp);
}
