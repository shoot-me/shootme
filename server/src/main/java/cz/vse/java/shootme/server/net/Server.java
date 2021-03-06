package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.server.game.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {

    private static Server server;

    private static int gameServerPort = 10000;

    private ServerSocket serverSocket;

    private Map<String, Connection> connections;

    private Map<String, Game> games;

    private static final Logger logger = LogManager.getLogger(Server.class);

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connections = new ConcurrentHashMap<>();
        games = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);

                connection.setOnClose(this::closeConnection);

                connections.put(connection.id, connection);

                logger.info("Accepting connection: " + connection.id);

                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(Connection connection) {
        logger.info("Closing connection: " + connection.id);

        connections.remove(connection.id);
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public synchronized int getGameServerPort() {
        gameServerPort++;

        return gameServerPort;
    }

    public synchronized Connection getConnection(String id) {
        return connections.get(id);
    }

    public Collection<Connection> getConnections() {
        return connections.values();
    }

    public synchronized static Server get() {
        if (server == null) {
            try {
                server = new Server(8080);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return server;
    }
}
