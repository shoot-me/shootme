package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.game.util.Vector;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.responses.JoinGameResponse;

public class JoinGame {

    public JoinGame(JoinGameRequest request) {
        Game game = Server.get().getGames().get(request.gameName);

        Player player = new Player("img/players/player_1.png", new Vector(0, 0), request.getConnection().getUser().getUsername());

        request.getConnection().setGame(game);
        request.getConnection().setPlayer(player);

        game.getConnections().add(request.getConnection());

        game.addEntity(player);

        request.respond(new JoinGameResponse(game.getConfiguration(), game.getState().copy()));
    }

}
