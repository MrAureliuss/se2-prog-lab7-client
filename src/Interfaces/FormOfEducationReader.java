package Interfaces;

import BasicClasses.FormOfEducation;

public interface FormOfEducationReader {
    boolean checkExist(String toContains);

    FormOfEducation read(boolean canBeNull);
}
