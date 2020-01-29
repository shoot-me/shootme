package cz.vse.java.shootme.server.net.requests;

public class GetStatisticsRequest extends Request {

    public final String order;

    public GetStatisticsRequest(String order) {
        this.order = order;
    }

    public GetStatisticsRequest() {
        this.order = "ASC";
    }

}
