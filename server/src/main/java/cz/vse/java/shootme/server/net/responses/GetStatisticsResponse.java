package cz.vse.java.shootme.server.net.responses;

import java.util.List;

public class GetStatisticsResponse extends Response {

    public final List<String> statistics;

    public GetStatisticsResponse(List<String> statistics) {
        this.statistics = statistics;
    }

}
