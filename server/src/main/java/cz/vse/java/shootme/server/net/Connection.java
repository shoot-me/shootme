package cz.vse.java.shootme.server.net;

import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.common.requests.Request;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread {

    private Socket socket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    Connection(Socket socket) throws IOException {
        this.socket = socket;

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = (Request) objectInputStream.readObject();

                request.setObjectOutputStream(objectOutputStream);

                EventBus.get().emit(request);
            } catch (Exception e) {
                return;
            }
        }
    }
}
