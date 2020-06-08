package Commands.Utils.Readers.EnumReaders;

import BasicClasses.FormOfEducation;
import Interfaces.FormOfEducationReader;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Считыватель формы обучения.
 */
public class FormOfEducationReaderImp implements FormOfEducationReader {
    @Override
    public boolean checkExist(String toContains) {
        return Arrays.stream(FormOfEducation.values()).anyMatch((formOfEducation) -> formOfEducation.name().equals(toContains.toUpperCase()));
    }

    @Override
    public FormOfEducation read(boolean canBeNull) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите страну из представленных(" + Arrays.asList(FormOfEducation.values()) + "): ");
        String toContains = in.nextLine().trim().toUpperCase();

        if ((!checkExist(toContains)) && !canBeNull && !toContains.equals("") || !canBeNull && toContains.equals("") || canBeNull && !toContains.equals("")) {
            while (!checkExist(toContains)) {
                System.out.print("Вы ввели несуществующее значение из представленных. Попробуйте снова: ");
                toContains = in.nextLine().trim().toUpperCase();
                checkExist(toContains);
                if (canBeNull && toContains.equals("")) { return null; }
            }
        }

        if (canBeNull && toContains.equals("")) { return null; }

        return Enum.valueOf(FormOfEducation.class, toContains);
    }
}
