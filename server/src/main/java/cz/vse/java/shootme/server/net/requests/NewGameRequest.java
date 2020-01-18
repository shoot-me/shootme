package cz.vse.java.shootme.server.net.requests;

public class NewGameRequest extends Request {

    public final String name;

    public NewGameRequest(String name) {
        this.name = name;
    }
}
