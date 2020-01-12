package cz.vse.java.shootme.common.requests;

public class NewGameRequest {

    public final String token;

    public final String name;

    public NewGameRequest(String token, String name) {
        this.token = token;
        this.name = name;
    }
}
