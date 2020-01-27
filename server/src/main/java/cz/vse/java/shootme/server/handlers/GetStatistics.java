package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Statistic;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.GetStatisticsRequest;
import cz.vse.java.shootme.server.net.responses.GetStatisticsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetStatistics {

    public GetStatistics(GetStatisticsRequest request) {
        Database.transaction(request, em -> {
            User user = em.merge(request.getConnection().getUser());

            List<Statistic> statistics = user.getStatistics();

            List<String> lines = statistics.stream()
                    .map(statistic -> statistic.getResult().getName() + " - kills: " + statistic.getKills())
                    .collect(Collectors.toList());

            request.respond(new GetStatisticsResponse(lines));
        });
    }

}
