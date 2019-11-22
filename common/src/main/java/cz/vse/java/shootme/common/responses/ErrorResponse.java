package cz.vse.java.shootme.common.responses;

public class ErrorResponse extends Response {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
