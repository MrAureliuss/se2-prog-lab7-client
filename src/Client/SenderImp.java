package Client;

import Interfaces.Sender;
import Interfaces.Session;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Singleton
public class SenderImp implements Sender {
    private final SocketChannel socketChannel;

    @Inject
    public SenderImp(Session session) {
        this.socketChannel = session.getSocketChannel();
    }

    @Override
    public void sendObject(Object serializedObject) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(serializedObject);
        byte [] data = bos.toByteArray();
        socketChannel.write(ByteBuffer.wrap(data));
    }
}
