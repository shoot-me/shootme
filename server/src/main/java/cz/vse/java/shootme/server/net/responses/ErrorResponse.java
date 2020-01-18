package cz.vse.java.shootme.server.net.responses;

public class ErrorResponse extends Response {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
