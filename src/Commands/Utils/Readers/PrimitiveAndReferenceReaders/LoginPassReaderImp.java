package Commands.Utils.Readers.PrimitiveAndReferenceReaders;
import Interfaces.CommandReceiver;
import Interfaces.HashEncrypter;
import Interfaces.LoginPassReader;
import com.google.inject.Inject;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

public class LoginPassReaderImp implements LoginPassReader {
    private final CommandReceiver commandReceiver;
    private final HashEncrypter hashEncrypter;

    @Inject
    public LoginPassReaderImp(CommandReceiver commandReceiver, HashEncrypter hashEncrypter) {
        this.commandReceiver = commandReceiver;
        this.hashEncrypter = hashEncrypter;
    }

    @Override
    public String[] tryAuthOrRegistration() throws InterruptedException, IOException, ClassNotFoundException {
        String login = "111";
        String password = hashEncrypter.encryptString("111");

//        Console console = System.console();
//        if (console == null){
//            System.out.println("Запускайте из консоли");
//            System.exit(0);
//        }
//
//        String type = console.readLine("Приветствуем! Вы уже зарегистрированы? [Да/Нет] ").trim().toUpperCase();
//        if (type.equals("ДА")) {
//            login = console.readLine("Введите логин: ");
//            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
//            password = hashEncrypter.encryptString(new String(passwordArray));
//
//        } else if (type.equals("НЕТ")) {
//            login = console.readLine("Введите логин: ");
//            char[] passwordArray = console.readPassword("Введите Ваш очень нужный пароль: ");
//            char[] passwordArrayCheck;
//
//            do {
//                passwordArrayCheck = console.readPassword("Введите Ваш очень нужный пароль еще раз: ");
//                password = hashEncrypter.encryptString(new String(passwordArray));
//            } while (!Arrays.equals(passwordArray, passwordArrayCheck));
//
//            commandReceiver.register(login, password);
//        } else { System.out.println("Такого варианта нет. В следующий раз будь осторожнее. Пока!"); System.exit(0);}

        return new String[] {login, password};
    }
}
