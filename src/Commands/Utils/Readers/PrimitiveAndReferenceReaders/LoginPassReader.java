package Commands.Utils.Readers.PrimitiveAndReferenceReaders;
import Commands.CommandReceiver;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

public class LoginPassReader {
    private CommandReceiver commandReceiver;

    public LoginPassReader(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public String[] tryAuthOrRegistration() throws InterruptedException, IOException, ClassNotFoundException {
        String login = "";
        String password = "";

        Console console = System.console();

        String type = console.readLine("Приветствуем! Вы уже зарегистрированы? [Да/Нет] ").trim().toUpperCase();
        if (type.equals("ДА")) {
            login = console.readLine("Введите логин: ");
            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
            password = new String(passwordArray);

        } else if (type.equals("НЕТ")) {
            login = console.readLine("Введите логин: ");
            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
            char[] passwordArrayCheck;

            do {
                passwordArrayCheck = console.readPassword("Введите Ваш очень нужный пароль еще раз: ");
                password = new String(passwordArray);
            } while (!Arrays.equals(passwordArray, passwordArrayCheck));

            commandReceiver.register(login, password);
        } else { System.out.println("Такого варианта нет. В следующий раз будь осторожнее. Пока!"); System.exit(0);}

        return new String[] {login, password};
    }
}
