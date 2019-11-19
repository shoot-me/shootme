package cz.vse.java.shootme.common.responses;

public class Error extends Response {

    private String message;

    public Error(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
