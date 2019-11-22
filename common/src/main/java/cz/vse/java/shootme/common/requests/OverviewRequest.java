package cz.vse.java.shootme.common.requests;

public class OverviewRequest extends Request {

    public final String token;

    public OverviewRequest(String token) {
        this.token = token;
    }
}
