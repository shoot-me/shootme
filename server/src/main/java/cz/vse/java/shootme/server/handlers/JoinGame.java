package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.game.State;
import cz.vse.java.shootme.common.game.entities.Player;
import cz.vse.java.shootme.common.game.util.Vector;
import cz.vse.java.shootme.common.requests.JoinGameRequest;
import cz.vse.java.shootme.common.responses.GameUpdateResponse;
import cz.vse.java.shootme.server.net.Server;

import java.util.Random;

public class JoinGame {

    public Random random = new Random();

    public JoinGame(JoinGameRequest request) {
        System.out.println("Player joined a game: " + request.playerName);

        State state = Server.get().getGame(request.gameName).getState();

        state.getEntities().add(new Player("img/players/player_1.png", new Vector(random.nextInt(600), random.nextInt(600)), new Vector(0, 0), request.playerName));

        request.respond(new GameUpdateResponse(state));
    }

}
