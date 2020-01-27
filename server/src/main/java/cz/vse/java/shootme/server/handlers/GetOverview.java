package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.OverviewRequest;
import cz.vse.java.shootme.server.net.responses.OverviewResponse;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class GetOverview {

    public GetOverview(OverviewRequest request) {
        User user = request.getConnection().getUser();

        OverviewResponse overviewResponse = new OverviewResponse();

        List<Configuration> configurations = Server.get().getGames().values().stream()
                .map(Game::getConfiguration)
                .collect(Collectors.toList());

        overviewResponse.configurations.addAll(configurations);

        Database.transaction(em -> {
            Query query = em.createQuery("from Skin S", Skin.class);

            ((List<Skin>) query.getResultList()).forEach(skin -> {
                overviewResponse.skins.put(skin.getPath(), skin.getName());
            });
        });

        overviewResponse.avatar = user.getSkin().getPath();

        request.respond(overviewResponse);
    }

}
