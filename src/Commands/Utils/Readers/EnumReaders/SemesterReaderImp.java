package Commands.Utils.Readers.EnumReaders;

import BasicClasses.Semester;
import Interfaces.SemesterReader;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Считыватель Семестра.
 */
public class SemesterReaderImp implements SemesterReader {
    @Override
    public boolean checkExist(String toContains) {
        return Arrays.stream(Semester.values()).anyMatch((semester) -> semester.name().equals(toContains.toUpperCase()));
    }

    @Override
    public Semester read(boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите страну из представленных(" + Arrays.asList(Semester.values()) + "): ");
        String toContains = in.nextLine().trim().toUpperCase();

        if ((!checkExist(toContains)) && !canBeNull && !toContains.equals("") || !canBeNull && toContains.equals("") || canBeNull && !toContains.equals("")) {
            while (!checkExist(toContains)) {
                System.out.print("Вы ввели несуществующее значение из представленных. Попробуйте снова: ");
                toContains = in.nextLine().trim().toUpperCase();
                checkExist(toContains);
            }
        }

        if (canBeNull && toContains.equals("")) { return null; }

        return Enum.valueOf(Semester.class, toContains);
    }
}
