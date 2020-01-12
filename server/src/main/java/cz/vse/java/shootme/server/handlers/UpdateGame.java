package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.game.State;
import cz.vse.java.shootme.common.requests.GameUpdateRequest;
import cz.vse.java.shootme.common.responses.GameUpdateResponse;
import cz.vse.java.shootme.server.net.Server;

public class UpdateGame {

    public UpdateGame(GameUpdateRequest request) {
        State state = Server.get().getGame(request.gameName).getState();

        System.out.println(state);

        request.respond(new GameUpdateResponse(state));
    }

}
