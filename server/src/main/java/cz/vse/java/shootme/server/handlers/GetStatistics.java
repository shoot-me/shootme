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
        List<String> lines = Database.transaction(em -> {
            User user = em.merge(request.getConnection().getUser());

            List<Statistic> statistics = user.getStatistics();

            if(request.order.equals("ASC")) {
                statistics.sort((a, b) -> a.getResult().getDateTime().isBefore(b.getResult().getDateTime()) ? -1 : 1);
            } else {
                statistics.sort((a, b) -> a.getResult().getDateTime().isAfter(b.getResult().getDateTime()) ? -1 : 1);
            }

            return statistics.stream()
                    .map(statistic -> statistic.getResult().getDateTime().toString() + " - " + statistic.getResult().getName() + " - kills: " + statistic.getKills())
                    .collect(Collectors.toList());
        }).orElse(null);

        if (lines != null) {
            request.respond(new GetStatisticsResponse(lines));
        } else {
            request.respondError("Error");
        }
    }

}
