import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ConsoleManager consoleManager = new ConsoleManager();
            consoleManager.startInteractiveMode(args[0], args[1], args[2], args[3], args[4]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Введено некорректное количество аргументов.\n" +
                    "Требуются 5 аргументов: адрес узла, порт, задержка, логин, пароль");
        }
    }
}