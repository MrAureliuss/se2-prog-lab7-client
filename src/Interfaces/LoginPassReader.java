package Interfaces;

import java.io.IOException;

public interface LoginPassReader {
    String[] tryAuthOrRegistration() throws InterruptedException, IOException, ClassNotFoundException;
}
