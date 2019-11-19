package cz.vse.java.shootme.common.requests;

import cz.vse.java.shootme.common.responses.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Request implements Serializable {

    private ObjectOutputStream objectOutputStream;

    public boolean respond(Response response) {
        try {
            objectOutputStream.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

}
