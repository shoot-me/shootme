package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class GameConnection implements Runnable {

    public final String id = UUID.randomUUID().toString();

    private Thread thread;

    private Socket socket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Consumer<GameConnection> onClose;

    private List<Action> actions;

    private User user;

    public GameConnection(Socket socket) throws IOException {
        this.thread = new Thread(this);
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.onClose = null;
        this.actions = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Action action = (Action) objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();

                break;
            }
        }

        try {
            socket.close();

            onClose.accept(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addAction(Action action) {
        actions.add(action);
    }

    public synchronized List<Action> consumeActions() {
        List<Action> consumed = new ArrayList<>(actions);

        actions.clear();

        return consumed;
    }

    public void setOnClose(Consumer<GameConnection> onClose) {
        this.onClose = onClose;
    }

    public void start() {
        try {
            String connectionId = (String) objectInputStream.readObject();
            user = Server.get().getConnection(connectionId).getUser();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        thread.start();
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public User getUser() {
        return user;
    }
}
