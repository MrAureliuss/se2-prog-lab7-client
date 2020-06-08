package Commands.Utils.Readers.PrimitiveAndReferenceReaders;

import Interfaces.RefIntReader;

import java.util.Scanner;

/**
 * Считыватель класса-оболочки int.
 */
public class RefIntReaderImp implements RefIntReader {
    @Override
    public Integer read(String messageForConsole, boolean canBeNull, int limit, String type) {
        System.out.print(messageForConsole);
        Scanner sc = new Scanner(System.in);
        int result = 0;
        boolean end = false;
        while (!end) {
            try {
                result = Integer.parseInt(sc.nextLine().trim());
                switch (type) {
                    case ("MIN"):
                        if (result > limit) { end = true; }
                        else { System.out.print("Вы ввели не подходящее значение. " + "Оно должно быть больше " + limit + ". Попробуйте снова: ");}
                        break;
                    case ("MAX"):
                        if (result < limit) { end = true; }
                        else { System.out.print("Вы ввели не подходящее значение. " + "Оно должно быть меньше " + limit + ". Попробуйте снова: ");}
                        break;
                }
            } catch (NumberFormatException ex) {
                if (canBeNull && sc.nextLine().trim().equals("")) {
                    return null;
                }
                System.out.print("Вы должны ввести число, попробуйте снова: ");
            }
        }

        return result;
    }
}
