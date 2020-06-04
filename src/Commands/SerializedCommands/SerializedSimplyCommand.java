package Commands.SerializedCommands;

import Commands.Command;

import java.io.Serializable;

public class SerializedSimplyCommand implements Serializable {
    private Command command;
    private String login;
    private String password;

    public SerializedSimplyCommand(Command command, String login, String password) {
        this.command = command;
        this.login = login;
        this.password = password;
    }

    public Command getCommand() {
        return command;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
