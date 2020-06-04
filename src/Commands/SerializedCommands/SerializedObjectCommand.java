package Commands.SerializedCommands;

import Commands.Command;

import java.io.Serializable;

public class SerializedObjectCommand implements Serializable {
    private Command command;
    private Object object;
    private String login;
    private String password;

    public SerializedObjectCommand(Command command, Object object, String login, String password) {
        this.command = command;
        this.object = object;
        this.login = login;
        this.password = password;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
