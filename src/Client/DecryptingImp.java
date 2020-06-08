package Client;

import Commands.SerializedCommands.*;
import Interfaces.Decrypting;
import com.google.inject.Singleton;

import java.io.IOException;

@Singleton
public class DecryptingImp implements Decrypting {
    @Override
    public void decrypt(Object o) throws IOException {
        if (o instanceof SerializedMessage) {
            SerializedMessage serializedMessage = (SerializedMessage) o;
            System.out.println(((SerializedMessage) o).getMessage());
        }
    }
}
