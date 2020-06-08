package Commands;

import Interfaces.CommandInvoker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Инвокер(вызыватель), выполяет команды. Хранит зарегистрированные команды.
 */
@Singleton
public class CommandInvokerImp implements CommandInvoker {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    @Inject
    public CommandInvokerImp(Set<Command> commands){
        register(commands.toArray(new Command[0]));
    }

    private void register(Command... commands) {
        Arrays.stream(commands).forEach(command -> commandMap.put(command.writeInfo().split(" ")[1], command));
    }

    @Override
    public void executeCommand(String[] commandName) {
        try {
            if (commandName.length > 0 && !commandName[0].equals("")) {
                Command command = commandMap.get(commandName[0]);
                command.execute(commandName);
            } else { System.out.println("Вы не ввели команду."); }
        } catch (NullPointerException ex) {
            System.out.println("Не существует команды " + commandName[0] + ". Для справки используйте – help");
        } catch (IllegalStateException | IOException | ClassNotFoundException | InterruptedException ex) {
            if (ex.getMessage().equals("Connection reset by peer")) {
                System.out.println("Там это... Сервер помер, но ты приходи в следующий раз и обязательно сможешь пошалить с коллекцией");
                System.exit(0);
            }
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
