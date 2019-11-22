package cz.vse.java.shootme.common.responses;

public class LoginSuccessfulResponse extends OkResponse {

    public final String token;

    public LoginSuccessfulResponse(String token) {
        this.token = token;
    }
}
