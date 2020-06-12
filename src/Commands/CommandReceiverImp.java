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
import java.net.PortUnreachableException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Ресивер(получатель), отправляет серилизованные объекты на сервер.
 */
@Singleton
public class CommandReceiverImp implements CommandReceiver {
    private final CommandInvoker commandInvoker;
    private final Sender sender;
    private final SocketChannel socketChannel;
    private final ElementCreator elementCreator;
    private final Receiver receiver;
    private final Register register;
    private final Selector selector;
    private String login;
    private String password;

    @Inject
    public CommandReceiverImp(CommandInvoker commandInvoker, Session session,
                              Sender sender, ElementCreator elementCreator, Receiver receiver, Register register) throws IOException {
        this.commandInvoker = commandInvoker;
        socketChannel = session.getSocketChannel();
        this.sender = sender;
        this.elementCreator = elementCreator;
        this.receiver = receiver;
        this.register = register;
        selector = Selector.open();
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
    public void info() throws ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("info"), login, password));
    }

    @Override
    public void show() throws ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("show"), login, password));
    }

    @Override
    public void add() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"),
                elementCreator.createStudyGroup(), login, password));
    }

    @Override
    public void update(String ID) throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                elementCreator.createStudyGroup(), ID, login, password));
    }

    @Override
    public void removeById(String ID) throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedArgumentCommand(commandInvoker.getCommandMap().get("remove_by_id"), ID, login, password));
    }

    @Override
    public void clear() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("clear"), login, password));
    }

    @Override
    public void exit() throws IOException {
        socketChannel.close();
        System.out.println("Завершение работы клиента.");
        System.exit(0);
    }

    @Override
    public void head() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("head"), login, password));
    }

    @Override
    public void removeGreater() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                elementCreator.createStudyGroup(), login, password));
    }

    @Override
    public void removeLower() throws ClassNotFoundException, InterruptedException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_lower"),
                elementCreator.createStudyGroup(), login, password));
    }

    @Override
    public void minBySemesterEnum() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("min_by_semester_enum"), login, password));
    }

    @Override
    public void maxByGroupAdmin() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedCommand(commandInvoker.getCommandMap().get("max_by_group_admin"), login, password));
    }

    @Override
    public void countByGroupAdmin() throws InterruptedException, ClassNotFoundException {
        if (login.equals("") || password.equals("")) {
            System.out.println("Вы не авторизированы");
            return;
        }
        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("count_by_group_admin"),
                elementCreator.createPerson(), login, password));
    }

    @Override
    public void register(String login, String password) throws InterruptedException, ClassNotFoundException {
        requestHandler(new SerializedCommand(register, login, password));
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
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("add"),
                                        studyGroup, login, password));
                                break;
                            case "update":
                                requestHandler(new SerializedCombinedCommand(commandInvoker.getCommandMap().get("update"),
                                        elementCreator.createStudyGroup(), command.split(" ")[1], login, password));
                                break;
                            case "remove_greater":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_greater"),
                                        studyGroup, login, password));
                                break;
                            case "remove_lower":
                                requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("remove_lower"),
                                        studyGroup, login, password));
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
                        requestHandler(new SerializedObjectCommand(commandInvoker.getCommandMap().get("count_by_group_admin"),
                                person, login, password));
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

    private void requestHandler(Object serializedObject) throws InterruptedException, ClassNotFoundException {
        try {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isWritable()) {
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        sender.sendObject(serializedObject);
                    }
                    if (selectionKey.isReadable()) {
                        receiver.receive(socketChannel);
                        return;
                    }
                }
            }
        } catch (PortUnreachableException e) {
            System.out.println("Не удалось получить данные по указанному порту/сервер не доступен");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
