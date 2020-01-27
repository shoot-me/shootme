package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class GameServer {

    public static final String id = UUID.randomUUID().toString();

    private Thread thread;

    private Thread sender;

    private Game game;

    private int port;

    private ServerSocket serverSocket;

    private Map<String, GameConnection> gameConnections;

    private BlockingQueue<StateUpdate> stateUpdates;

    private static final Logger logger = LogManager.getLogger(GameServer.class);

    public GameServer(Game game, int port) throws IOException {
        this.thread = new Thread(this::run);
        this.sender = new Thread(this::send);
        this.game = game;
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

                logger.info("Accepting game connection: " + gameConnection.id);

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
                continue;
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

    public List<Action> consumeActions() {
        List<Action> actions = new ArrayList<>();

        for (GameConnection gameConnection : gameConnections.values()) {
            actions.addAll(gameConnection.consumeActions());
        }

        return actions;
    }

    public void closeGameConnection(GameConnection gameConnection) {
        logger.info("Closing game connection: " + gameConnection.id);

        gameConnections.remove(gameConnection.id);

        game.getState().removeEntity(gameConnection.getPlayer());
    }

    public void start() {
        thread.start();
        sender.start();
    }

    public int getPort() {
        return port;
    }
}
