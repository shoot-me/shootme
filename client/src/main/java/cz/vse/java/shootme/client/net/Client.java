package cz.vse.java.shootme.client.net;

import cz.vse.java.shootme.server.net.requests.Request;
import cz.vse.java.shootme.server.net.responses.Response;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Client client;

    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private Client(String address, int port) throws IOException {
        socket = new Socket(address, port);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Response send(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            objectOutputStream.reset();

            return (Response) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();

            System.exit(1);
        }

        return null;
    }

    public static boolean connect(String address, int port) {
        System.out.println("Connecting to: " + address + ":" + port);

        if (client != null) {
            try {
                client.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            client = new Client(address, port);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static Client get() {
        if (client == null) {
            System.err.println("No connection to server!");
            System.exit(1);
        }

        return client;
    }
}
