package cz.vse.java.shootme.client.net;

import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.actions.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameClient {

    private static GameClient gameClient;

    private Thread receiver;

    private Thread sender;

    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private BlockingQueue<StateUpdate> stateUpdates;

    private BlockingQueue<Action> actions;

    private GameClient(String address, int port) throws IOException {
        this.receiver = new Thread(this::receive);
        this.sender = new Thread(this::send);
        this.socket = new Socket(address, port);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.stateUpdates = new LinkedBlockingQueue<>();
        this.actions = new LinkedBlockingQueue<>();
    }

    private void receive() {
        while (true) {
            try {
                StateUpdate stateUpdate = (StateUpdate) objectInputStream.readObject();

                stateUpdates.put(stateUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void send() {
        while (true) {
            try {
                Action action = actions.take();

                objectOutputStream.writeObject(action);
                objectOutputStream.flush();
                objectOutputStream.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect(String address, int port, String connectionId) {
        if (gameClient != null) {
            try {
                gameClient.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            gameClient = new GameClient(address, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            gameClient.objectOutputStream.writeObject(connectionId);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        gameClient.receiver.start();
        gameClient.sender.start();
    }

    public BlockingQueue<StateUpdate> getStateUpdates() {
        return stateUpdates;
    }

    public BlockingQueue<Action> getActions() {
        return actions;
    }

    public static GameClient get() {
        if (gameClient == null) {
            System.err.println("No connection to game server!");
            System.exit(1);
        }

        return gameClient;
    }

}
