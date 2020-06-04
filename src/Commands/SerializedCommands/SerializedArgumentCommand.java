package Commands.SerializedCommands;

import Commands.Command;
import Commands.SerializedCommand;

import java.io.Serializable;

public class SerializedArgumentCommand extends SerializedCommand implements Serializable {
    private String arg;

    public SerializedArgumentCommand(Command command, String arg, String login, String password) {
        super(command, login, password);
        this.arg = arg;
    }

    public String getArg() {
        return arg;
    }
}
