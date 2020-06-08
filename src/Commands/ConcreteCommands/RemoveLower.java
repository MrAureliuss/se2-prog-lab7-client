package Commands.ConcreteCommands;

import Commands.Command;
import Interfaces.CommandReceiver;
import com.google.inject.Inject;

import java.io.IOException;

/**
 * Конкретная команда удаления объектов, меньше заданного.
 */
public class RemoveLower extends Command {
    private static final long serialVersionUID = 32L;
    transient private final CommandReceiver commandReceiver;

    @Inject
    public RemoveLower(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected void execute(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде remove_lower.");
        }
        commandReceiver.removeLower();
    }

    @Override
    protected String writeInfo() {
        return "Команда remove_lower – удалить из коллекции все элементы, меньше, чем заданный.";
    }
}
