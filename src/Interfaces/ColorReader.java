package Interfaces;

import BasicClasses.Color;

public interface ColorReader {
    boolean checkExist(String toContains);

    Color read(String messageForConsole, boolean canBeNull);
}
