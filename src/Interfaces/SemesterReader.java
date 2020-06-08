package Interfaces;

import BasicClasses.Semester;

public interface SemesterReader {
    boolean checkExist(String toContains);

    Semester read(boolean canBeNull);
}
