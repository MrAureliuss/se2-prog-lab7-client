package Commands;

import BasicClasses.Person;
import BasicClasses.StudyGroup;
import Client.Receiver;
import Client.Sender;
import Commands.ConcreteCommands.*;
import Commands.SerializedCommands.SerializedArgumentCommand;
import Commands.SerializedCommands.SerializedCombinedCommand;
import Commands.SerializedCommands.SerializedObjectCommand;
import Commands.SerializedCommands.SerializedSimplyCommand;
import Commands.Utils.Creaters.ElementCreator;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * Ресивер(получатель), отправляет серилизованные объекты на сервер.
 */
public class CommandReceiver {
    private final CommandInvoker commandInvoker;
    private final Sender sender;
    private final SocketChannel socketChannel;
    private final Integer delay;
    private final ElementCreator elementCreator;

    public CommandReceiver(CommandInvoker commandInvoker, Sender sender, SocketChannel socketChannel, Integer delay, ElementCreator elementCreator) {
        this.commandInvoker = commandInvoker;
        this.sender = sender;
        this.socketChannel = socketChannel;
        this.delay = delay;
        this.elementCreator = elementCreator;
    }

    public void help() {
        commandInvoker.getCommandMap().forEach((name, command) -> command.writeInfo());
    }

    public void info() throws IOException, ClassNotFoundException, InterruptedException {
        sender.sendObject(new SerializedSimplyCommand(new Info()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void show() throws IOException, ClassNotFoundException, InterruptedException {
        sender.sendObject(new SerializedSimplyCommand(new Show()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void add() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedObjectCommand(new Add(), elementCreator.createStudyGroup()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public void update(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedCombinedCommand(new Update(), elementCreator.createStudyGroup(), ID));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    /**
     *
     * @param ID - удаление по ID.
     */
    public void removeById(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedArgumentCommand(new RemoveByID(), ID));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void clear() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedSimplyCommand(new Clear()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void exit() throws IOException {
        socketChannel.close();
        System.out.println("Завершение работы клиента.");
        System.exit(0);
    }

    public void head() throws IOException, InterruptedException, ClassNotFoundException {
       sender.sendObject(new SerializedSimplyCommand(new Head()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void removeGreater() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedObjectCommand(new RemoveGreater(), elementCreator.createStudyGroup()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void removeLower() throws IOException, ClassNotFoundException, InterruptedException {
        sender.sendObject(new SerializedObjectCommand(new RemoveLower(), elementCreator.createStudyGroup()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void minBySemesterEnum() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedSimplyCommand(new MinBySemesterEnum()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void maxByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedSimplyCommand(new MaxByGroupAdmin()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void countByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedObjectCommand(new CountByGroupAdmin(), elementCreator.createPerson()));
        Thread.sleep(delay);
        Receiver.receive(socketChannel);
    }

    public void executeScript(String path) {
        String line;
        String command;
        ArrayList<String> parameters = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(" ")[0].matches("add|update|remove_lower|remove_greater")) {
                    parameters.clear();
                    command = line;
                    for (int i = 0; i < 11; i++) {
                        if (line != null) {
                            line = bufferedReader.readLine();
                            parameters.add(line);
                        } else { System.out.println("Не хватает параметров для создания объекта."); break; }
                    }
                    StudyGroup studyGroup = elementCreator.createScriptStudyGroup(parameters);
                    if (studyGroup != null) {
                        switch (command.split(" ")[0]) {
                            case "add":
                                sender.sendObject(new SerializedObjectCommand(new Add(), studyGroup));
                                Thread.sleep(delay);
                                Receiver.receive(socketChannel);
                                break;
                            case "update":
                                sender.sendObject(new SerializedCombinedCommand(new Update(), elementCreator.createStudyGroup(), command.split(" ")[1]));
                                Thread.sleep(delay);
                                Receiver.receive(socketChannel);
                                break;
                            case "remove_greater":
                                sender.sendObject(new SerializedObjectCommand(new RemoveGreater(), studyGroup));
                                Thread.sleep(delay);
                                Receiver.receive(socketChannel);
                                break;
                            case "remove_lower":
                                sender.sendObject(new SerializedObjectCommand(new RemoveLower(), studyGroup));
                                Thread.sleep(delay);
                                Receiver.receive(socketChannel);
                                break;
                        }
                    }
                } else if(line.split(" ")[0].equals("count_by_group_admin")) {
                    parameters.clear();
                    for (int i = 0; i < 5; i++) {
                        if (line != null) {
                            line = bufferedReader.readLine();
                            parameters.add(line);
                        } else { System.out.println("Не хватает параметров для создания объекта."); break; }
                    }
                    Person person = elementCreator.createScriptPerson(parameters);
                    if (person != null) {
                        sender.sendObject(new SerializedObjectCommand(new CountByGroupAdmin(), person));
                        Thread.sleep(delay);
                        Receiver.receive(socketChannel);
                    }
                } else if (line.split(" ")[0].equals("execute_script")
                        && line.split(" ")[1].equals(ExecuteScript.getPath())) { System.out.println("Пресечена попытка рекурсивного вызова скрипта."); }
                else { commandInvoker.executeCommand(line.split(" ")); }
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}
