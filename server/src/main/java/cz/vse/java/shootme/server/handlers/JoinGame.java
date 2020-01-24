package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.JoinGameRequest;
import cz.vse.java.shootme.server.net.requests.UpdateStatisticsRequest;
import cz.vse.java.shootme.server.net.responses.JoinGameResponse;

import java.util.Random;

public class JoinGame {

    private Random random = new Random();

    public JoinGame(JoinGameRequest request) {
        Game game = Server.get().getGames().get(request.gameName);

        String playerName = request.getConnection().getUser().getUsername();
        double x = random.nextInt((game.getConfiguration().getWidth() - 3) * 64) + 64;
        double y = random.nextInt((game.getConfiguration().getHeight() - 3) * 64) + 64;

        Player player = new Player("img/players/" + request.avatar + ".png", new Vector(x, y), playerName, request.getConnection().getUser());

        game.getState().addEntity(player);

        request.getConnection().setPlayer(player);

        request.respond(new JoinGameResponse(game.getPort(), game.getConfiguration()));


    }

}
