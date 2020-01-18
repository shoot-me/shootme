package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.net.requests.GameUpdateRequest;
import cz.vse.java.shootme.server.net.responses.GameUpdateResponse;

public class UpdateGame {

    public UpdateGame(GameUpdateRequest request) {
        Game game = request.getConnection().getGame();

        game.getActions().addAll(request.actions);

        request.respond(new GameUpdateResponse(game.getState()));
    }

}
