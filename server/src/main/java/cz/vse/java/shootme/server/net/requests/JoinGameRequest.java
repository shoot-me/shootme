package cz.vse.java.shootme.server.net.requests;

public class JoinGameRequest extends Request {

    public final String gameName;

    public JoinGameRequest(String gameName) {
        this.gameName = gameName;
    }
}
