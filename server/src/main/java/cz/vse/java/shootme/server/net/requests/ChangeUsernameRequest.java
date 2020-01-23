package cz.vse.java.shootme.server.net.requests;

public class ChangeUsernameRequest extends Request {

    public final String username;

    public ChangeUsernameRequest(String username){
        this.username = username;
    }
}
