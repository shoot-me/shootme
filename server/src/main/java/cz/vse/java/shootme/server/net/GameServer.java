package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.common.net.StateUpdate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class GameServer {

    public static final String id = UUID.randomUUID().toString();

    private Thread thread;

    private Thread sender;

    private int port;

    private ServerSocket serverSocket;

    private Map<String, GameConnection> gameConnections;

    private BlockingQueue<StateUpdate> stateUpdates;

    public GameServer(int port) throws IOException {
        this.thread = new Thread(this::run);
        this.sender = new Thread(this::send);
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.gameConnections = new ConcurrentHashMap<>();
        this.stateUpdates = new LinkedBlockingQueue<>();
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                GameConnection gameConnection = new GameConnection(socket);

                gameConnection.setOnClose(this::closeGameConnection);

                gameConnections.put(gameConnection.id, gameConnection);

                gameConnection.start();
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    public void send() {
        while (true) {
            try {
                StateUpdate stateUpdate = stateUpdates.take();

                for (GameConnection gameConnection : gameConnections.values()) {
                    gameConnection.getObjectOutputStream().writeObject(stateUpdate);
                    gameConnection.getObjectOutputStream().flush();
                    gameConnection.getObjectOutputStream().reset();
                }

            } catch (Exception e) {
                e.printStackTrace();

                break;
            }
        }
    }

    public void pushStateUpdate(StateUpdate stateUpdate) {
        try {
            stateUpdates.put(stateUpdate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeGameConnection(GameConnection gameConnection) {
        gameConnections.remove(gameConnection.id);
    }

    public void start() {
        thread.start();
        sender.start();
    }

    public int getPort() {
        return port;
    }
}
