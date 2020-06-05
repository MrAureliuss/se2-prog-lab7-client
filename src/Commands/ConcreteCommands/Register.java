package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * Конкретная команда для регистрации.
 */
public class Register extends Command {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;

    public Register (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Register() {}

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 3) { commandReceiver.registerOrAuth(args[1], args[2], "register"); }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда register. Синтаксис: register login password – зарегистрировать клиента.");
    }
}
