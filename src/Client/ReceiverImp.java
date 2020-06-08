package Client;

import Interfaces.Decrypting;
import Interfaces.Receiver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Singleton
public class ReceiverImp implements Receiver {
    private final Decrypting decrypting;

    @Inject
    public ReceiverImp(Decrypting decrypting) {
        this.decrypting = decrypting;
    }

    @Override
    public void receive(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int n;
        int i=0;
        while ((n=socketChannel.read(byteBuffer))>0) {
            i++;
            byteBuffer.flip();
            byteArrayOutputStream.write(byteBuffer.array(),0, n);
            byteBuffer.clear();
        }
        if(i!=0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            decrypting.decrypt(objectInputStream.readObject());
        }

    }
}
