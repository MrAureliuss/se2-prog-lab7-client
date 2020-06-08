package Interfaces;

import java.io.IOException;

public interface CommandReceiver {
    void setAuthorizationData(String login, String password);

    void help();

    void info() throws IOException, ClassNotFoundException, InterruptedException;

    void show() throws IOException, ClassNotFoundException, InterruptedException;

    void add() throws IOException, InterruptedException, ClassNotFoundException;

    void update(String ID) throws IOException, InterruptedException, ClassNotFoundException;

    void removeById(String ID) throws IOException, InterruptedException, ClassNotFoundException;

    void clear() throws IOException, InterruptedException, ClassNotFoundException;

    void exit() throws IOException;

    void head() throws IOException, InterruptedException, ClassNotFoundException;

    void removeGreater() throws IOException, InterruptedException, ClassNotFoundException;

    void removeLower() throws IOException, ClassNotFoundException, InterruptedException;

    void minBySemesterEnum() throws IOException, InterruptedException, ClassNotFoundException;

    void maxByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException;

    void countByGroupAdmin() throws IOException, InterruptedException, ClassNotFoundException;

    void register(String login, String password) throws IOException, InterruptedException, ClassNotFoundException;

    void executeScript(String path);
}
