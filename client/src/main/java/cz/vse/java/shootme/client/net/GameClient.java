package cz.vse.java.shootme.client.net;

import cz.vse.java.shootme.common.net.StateUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GameClient {

    private static GameClient gameClient;

    private Thread thread;

    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private BlockingQueue<StateUpdate> stateUpdates;

    private GameClient(String address, int port) throws IOException {
        this.thread = new Thread(this::run);
        this.socket = new Socket(address, port);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.stateUpdates = new LinkedBlockingDeque<>();
    }

    private void run() {
        while (true) {
            try {
                StateUpdate stateUpdate = (StateUpdate) objectInputStream.readObject();

                stateUpdates.put(stateUpdate);
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

        gameClient.thread.start();
    }

    public BlockingQueue<StateUpdate> getStateUpdates() {
        return stateUpdates;
    }

    public static GameClient get() {
        if (gameClient == null) {
            System.err.println("No connection to game server!");
            System.exit(1);
        }

        return gameClient;
    }

}
