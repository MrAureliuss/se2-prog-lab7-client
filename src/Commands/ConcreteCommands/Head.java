package Commands.ConcreteCommands;

import Commands.Command;
import Commands.CommandReceiver;

import java.io.IOException;

/**
 * Конкретная команда вывода первого элемента коллекции.
 */
public class Head extends Command {
    private static final long serialVersionUID = 32L;
    transient private CommandReceiver commandReceiver;

    public Head (CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public Head() {}

    @Override
    protected void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length > 1) {
            System.out.println("Введен не нужный аргумент. Команда приведена к базовой команде head.");
        }
        commandReceiver.head();
    }

    @Override
    protected void writeInfo() {
        System.out.println("Команда head – вывести первый элемент коллекции.");
    }
}
