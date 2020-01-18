package cz.vse.java.shootme.server.net.responses;

public class ErrorResponse extends Response {

    public final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
