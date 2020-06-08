package Commands.ConcreteCommands;

import Commands.Command;
import Interfaces.CommandReceiver;
import com.google.inject.Inject;

import java.io.IOException;
import java.io.Serializable;

/**
 * Конкретная команда получения информации о коллекции.
 */
public class Info extends Command implements Serializable {
    private static final long serialVersionUID = 32L;
    transient private final CommandReceiver commandReceiver;

    @Inject
    public Info (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде info.");
        }
        commandReceiver.info();
    }

    @Override
    protected String writeInfo() {
        return "Команда info – вывести в стандартный поток вывода информацию о коллекции.";
    }
}
