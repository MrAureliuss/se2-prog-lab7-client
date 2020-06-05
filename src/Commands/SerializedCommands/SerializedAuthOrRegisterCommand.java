package Commands.SerializedCommands;

import Commands.Command;
import Commands.SerializedCommand;
import Commands.Utils.HashEncrypter;

import java.io.Serializable;

public class SerializedAuthOrRegisterCommand extends SerializedCommand implements Serializable {
    private String login;
    private String password;
    private String type;

    public SerializedAuthOrRegisterCommand(Command command, String login, String password, String type) {
        super(command, login, HashEncrypter.encryptString(password));
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
