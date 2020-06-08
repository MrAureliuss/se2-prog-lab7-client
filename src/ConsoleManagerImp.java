import Commands.ConcreteCommands.*;
import Interfaces.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс управления и регистрацией консолью.
 */
@Singleton
class ConsoleManagerImp implements ConsoleManager {
    private final CommandInvoker commandInvoker;
    private final Sender sender;
    private final Session session;
    private final LoginPassReader loginPassReader;
    private final CommandReceiver commandReceiver;

    @Inject
    ConsoleManagerImp(CommandInvoker commandInvoker, Sender sender,
                      Session session, LoginPassReader loginPassReader, CommandReceiver commandReceiver) {
        this.commandInvoker = commandInvoker;
        this.sender = sender;
        this.session = session;
        this.loginPassReader = loginPassReader;
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void startInteractiveMode() throws IOException, ClassNotFoundException, InterruptedException {
        String login;
        String password;

        String[] data = loginPassReader.tryAuthOrRegistration();
        login = data[0];
        password = data[1];
        commandReceiver.setAuthorizationData(login, password);

        try(Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                commandInvoker.executeCommand(scanner.nextLine().trim().split(" "));
            }
        }

        session.closeSession();
    }
}
