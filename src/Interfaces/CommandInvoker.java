package Interfaces;

import Commands.Command;

import java.util.HashMap;

public interface CommandInvoker {
    void executeCommand(String[] commandName);

    HashMap<String, Command> getCommandMap();
}
