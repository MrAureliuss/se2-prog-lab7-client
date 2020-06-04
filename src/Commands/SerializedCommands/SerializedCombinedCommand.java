package Commands.SerializedCommands;

import Commands.Command;

import java.io.Serializable;

public class SerializedCombinedCommand implements Serializable {
    private Command command;
    private Object object;
    private String arg;
    private String login;
    private String password;

    public SerializedCombinedCommand(Command command, Object object, String arg, String login, String password) {
        this.command = command;
        this.object = object;
        this.arg = arg;
        this.login = login;
        this.password = password;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArg() {
        return arg;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
