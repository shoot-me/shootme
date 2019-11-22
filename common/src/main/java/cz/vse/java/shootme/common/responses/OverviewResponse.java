package cz.vse.java.shootme.common.responses;

public class OverviewResponse extends OkResponse {

    public final String username;

    public OverviewResponse(String username) {
        this.username = username;
    }

}
