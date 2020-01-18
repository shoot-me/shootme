package cz.vse.java.shootme.server.net.responses;

public class LoginSuccessfulResponse extends Response {

    public final String token;

    public LoginSuccessfulResponse(String token) {
        this.token = token;
    }
}
