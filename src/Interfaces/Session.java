package Interfaces;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface Session {
    void closeSession() throws IOException;

    SocketChannel getSocketChannel();
}
