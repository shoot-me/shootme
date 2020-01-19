package cz.vse.java.shootme.server.net.responses;

public class LoginSuccessfulResponse extends Response {

    public final String connectionId;

    public LoginSuccessfulResponse(String connectionId) {
        this.connectionId = connectionId;
    }

}
