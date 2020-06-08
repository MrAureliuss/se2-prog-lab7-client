package Interfaces;

import BasicClasses.Country;

public interface CountryReader {
    boolean checkExist(String toContains);

    Country read(String messageForConsole, boolean canBeNull);
}
