package cz.vse.java.shootme.server.net.requests;

public class SetFriendRequest extends Request {

    public final String username;

    public SetFriendRequest(String username){
        this.username = username;
    }
}
