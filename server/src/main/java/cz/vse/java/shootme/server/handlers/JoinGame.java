package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.responses.JoinGameResponse;

public class JoinGame {

    public JoinGame(JoinGameRequest request) {
        Game game = Server.get().getGames().get(request.gameName);

        request.respond(new JoinGameResponse(game.getPort(), game.getConfiguration()));
    }

}
