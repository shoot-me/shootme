package cz.vse.java.shootme.server.net.requests;

import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.responses.ErrorResponse;
import cz.vse.java.shootme.server.net.responses.Response;
import cz.vse.java.shootme.server.net.responses.SuccessResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public abstract class Request implements Serializable {

    public final String id = UUID.randomUUID().toString();

    protected Connection connection;

    public synchronized void respond(Response response) {
        try {
            connection.writeResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void respondSuccess(String message) {
        respond(new SuccessResponse(message));
    }

    public void respondError(String message) {
        respond(new ErrorResponse(message));
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
