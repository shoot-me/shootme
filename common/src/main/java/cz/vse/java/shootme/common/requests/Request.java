package cz.vse.java.shootme.common.requests;

import cz.vse.java.shootme.common.responses.ErrorResponse;
import cz.vse.java.shootme.common.responses.OkResponse;
import cz.vse.java.shootme.common.responses.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Request implements Serializable {

    private ObjectOutputStream objectOutputStream;

    public boolean respond(Response response) {
        try {
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean respondOk() {
        return respond(new OkResponse());
    }

    public boolean respondError(String message) {
        return respond(new ErrorResponse(message));
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

}
