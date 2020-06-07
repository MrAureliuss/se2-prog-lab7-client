import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        try {
            ConsoleManager consoleManager = new ConsoleManager();
            consoleManager.startInteractiveMode(args[0], args[1], args[2]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Введено некорректное количество аргументов.\n" +
                    "Требуются 3 аргументa: адрес узла, порт, задержка");
        }
    }
}