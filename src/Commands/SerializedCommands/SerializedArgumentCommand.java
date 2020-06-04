package Commands.SerializedCommands;

import Commands.Command;

import java.io.Serializable;

public class SerializedArgumentCommand implements Serializable {
    private Command command;
    private String arg;
    private String login;
    private String password;

    public SerializedArgumentCommand(Command command, String arg, String login, String password) {
        this.command = command;
        this.arg = arg;
        this.login = login;
        this.password = password;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
