package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.OverviewRequest;
import cz.vse.java.shootme.server.net.responses.OverviewResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetOverview {

    public GetOverview(OverviewRequest request) {
        List<Configuration> configurations = Server.get().getGames().values().stream()
                .map(Game::getConfiguration)
                .collect(Collectors.toList());

        request.respond(new OverviewResponse(configurations));
    }

}
