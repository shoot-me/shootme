package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.Request;
import cz.vse.java.shootme.server.net.responses.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.function.Consumer;

public class Connection extends Thread {

    private static final Logger logger = LogManager.getLogger(Connection.class);

    public final String id = UUID.randomUUID().toString();

    private Socket socket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    private Consumer<Connection> onClose;

    private User user;

    private Player player;

    Connection(Socket socket) throws IOException {
        this.socket = socket;

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        logger.info("Start connection {}", id);

        while (true) {
            try {
                Request request = (Request) objectInputStream.readObject();

                request.setConnection(this);

                EventBus.get().emit(request);
            } catch (Exception e) {
                break;
            }
        }

        try {
            socket.close();

            onClose.accept(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Stop connection {}", id);
    }

    public synchronized void writeResponse(Response response) throws IOException {
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        objectOutputStream.reset();
    }

    public void setOnClose(Consumer<Connection> onClose) {
        this.onClose = onClose;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
