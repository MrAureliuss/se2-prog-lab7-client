package Interfaces;

import java.io.IOException;

public interface Sender {
    void sendObject(Object serializedObject) throws IOException;
}
