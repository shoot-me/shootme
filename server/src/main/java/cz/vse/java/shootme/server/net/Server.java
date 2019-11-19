package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.server.models.User;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

    private ServerSocket serverSocket;

    private Map<String, Connection> connections;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connections = Collections.synchronizedMap(new HashMap<>());
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
}
