package Commands;

import java.io.Serializable;

public class SerializedCommand implements Serializable {
    private Command command;
    private String login;
    private String password;

    public SerializedCommand(Command command, String login, String password) {
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

