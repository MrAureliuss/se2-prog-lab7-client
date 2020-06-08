package Commands;

import BasicClasses.Person;
import BasicClasses.StudyGroup;
import Commands.ConcreteCommands.*;
import Commands.SerializedCommands.SerializedArgumentCommand;
import Commands.SerializedCommands.SerializedCombinedCommand;
import Commands.SerializedCommands.SerializedObjectCommand;
import Interfaces.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * Ресивер(получатель), отправляет серилизованные объекты на сервер.
 */
@Singleton
public class CommandReceiverImp implements CommandReceiver {
    private final CommandInvoker commandInvoker;
    private final Sender sender;
    private final SocketChannel socketChannel;
    private final Integer delay = 500;
    private final ElementCreator elementCreator;
    private final Receiver receiver;
    private final Register register;
    private String login;
    private String password;

    @Inject
    public CommandReceiverImp(CommandInvoker commandInvoker, Session session,
                              Sender sender, ElementCreator elementCreator, Receiver receiver, Register register) {
        this.commandInvoker = commandInvoker;
        socketChannel = session.getSocketChannel();
        this.sender = sender;
        this.elementCreator = elementCreator;
        this.receiver = receiver;
        this.register = register;
    }

    @Override
    public void setAuthorizationData(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Override
    public void help() {
        commandInvoker.getCommandMap().forEach((name, command) -> System.out.println(command.writeInfo()));
    }

    @Override
    public void info() throws IOException, ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("info"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void show() throws IOException, ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("show"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void add() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"), elementCreator.createStudyGroup(), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void update(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                elementCreator.createStudyGroup(), ID, login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void removeById(String ID) throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedArgumentCommand(commandInvoker.getCommandMap().get("remove_by_id"), ID, login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void clear() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("clear"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void exit() throws IOException {
        socketChannel.close();
        System.out.println("Завершение работы клиента.");
        System.exit(0);
    }

    @Override
    public void head() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("head"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void removeGreater() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                elementCreator.createStudyGroup(), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void removeLower() throws IOException, ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_lower"),
                elementCreator.createStudyGroup(), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void minBySemesterEnum() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("min_by_semester_enum"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void maxByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedCommand(commandInvoker.getCommandMap().get("max_by_group_admin"), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void countByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("count_by_group_admin"),
                elementCreator.createPerson(), login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void register(String login, String password) throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializedCommand(register, login, password));
        Thread.sleep(delay);
        receiver.receive(socketChannel);
    }

    @Override
    public void executeScript(String path) {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
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
                                sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"),
                                        studyGroup, login, password));
                                Thread.sleep(delay);
                                receiver.receive(socketChannel);
                                break;
                            case "update":
                                sender.sendObject(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                                        elementCreator.createStudyGroup(), command.split(" ")[1], login, password));
                                Thread.sleep(delay);
                                receiver.receive(socketChannel);
                                break;
                            case "remove_greater":
                                sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                                        studyGroup, login, password));
                                Thread.sleep(delay);
                                receiver.receive(socketChannel);
                                break;
                            case "remove_lower":
                                sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_lower"),
                                        studyGroup, login, password));
                                Thread.sleep(delay);
                                receiver.receive(socketChannel);
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
                        sender.sendObject(new SerializedObjectCommand(commandInvoker.getCommandMap().get("count_by_group_admin"),
                                person, login, password));
                        Thread.sleep(delay);
                        receiver.receive(socketChannel);
                    }
                } else if (line.split(" ")[0].equals("execute_script")
                        && line.split(" ")[1].equals(((ExecuteScript)commandInvoker.getCommandMap().get("execute_script")).getPath())) {
                    System.out.println("Пресечена попытка рекурсивного вызова скрипта.");
                }
                else { commandInvoker.executeCommand(line.split(" ")); }
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            System.out.println("Ошибка! " + e.getMessage());
        }
    }
}
