package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.server.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {

    private static Server server;

    private ServerSocket serverSocket;

    private Map<String, Connection> connections;

    private Map<String, Game> games;

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connections = new ConcurrentHashMap<>();
        games = new ConcurrentHashMap<>();

        games.put("default", new Game("default"));
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public Game getGame(String name) {
        return games.computeIfAbsent(name, e -> new Game(name));
    }

    public static Server get() {
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
