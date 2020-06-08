import Interfaces.ConsoleManager;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Injector injector = Guice.createInjector(new ClientModule());
        ConsoleManager consoleManager = injector.getInstance(ConsoleManager.class);
        consoleManager.startInteractiveMode();
    }
}