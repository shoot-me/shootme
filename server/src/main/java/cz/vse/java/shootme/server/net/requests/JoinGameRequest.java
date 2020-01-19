package cz.vse.java.shootme.server.net.requests;

public class JoinGameRequest extends Request {

    public final String gameName;

    public final String avatar;

    public JoinGameRequest(String gameName, String avatar) {
        this.gameName = gameName;
        this.avatar = avatar;
    }
}
