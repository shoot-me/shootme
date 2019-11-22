package cz.vse.java.shootme.client.net;

import cz.vse.java.shootme.common.requests.Request;
import cz.vse.java.shootme.common.responses.Response;

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

    public Socket getSocket() {
        return socket;
    }

    public Response sendSync(Request request) throws IOException {
        objectOutputStream.writeObject(request);

        try {
            return (Response) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public static void connect(String address, int port) {
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
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Client get() {
        if (client == null) {
            System.err.println("No connection to server!");
            System.exit(1);
        }

        return client;
    }
}
