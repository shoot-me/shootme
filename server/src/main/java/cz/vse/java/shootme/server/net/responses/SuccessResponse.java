package cz.vse.java.shootme.server.net.responses;

public class SuccessResponse extends Response {

    public final String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

}
