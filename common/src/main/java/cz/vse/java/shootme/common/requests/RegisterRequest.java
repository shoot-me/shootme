package cz.vse.java.shootme.common.requests;

public class RegisterRequest extends Request {

    public final String username;

    public final String password;

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
