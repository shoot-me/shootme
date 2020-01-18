package cz.vse.java.shootme.server.net.responses;

public class OverviewResponse extends Response {

    public final String username;

    public OverviewResponse(String username) {
        this.username = username;
    }

}
