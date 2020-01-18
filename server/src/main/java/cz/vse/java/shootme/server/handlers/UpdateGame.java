package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.game.State;
import cz.vse.java.shootme.common.game.actions.Action;
import cz.vse.java.shootme.common.game.actions.KeyPressAction;
import cz.vse.java.shootme.common.game.util.Vector;
import cz.vse.java.shootme.server.net.requests.GameUpdateRequest;
import cz.vse.java.shootme.server.net.responses.GameUpdateResponse;
import cz.vse.java.shootme.server.net.Server;

public class UpdateGame {

    public UpdateGame(GameUpdateRequest request) {
        State state = Server.get().getGame(request.gameName).getState();

        for (Action action : request.actions) {
            if (action instanceof KeyPressAction) {
                if (((KeyPressAction) action).code.equals("W")) {
                    state.getPlayer(request.playerName).pos.add(new Vector(0, -10));
                }

                if (((KeyPressAction) action).code.equals("A")) {
                    state.getPlayer(request.playerName).pos.add(new Vector(-10, 0));
                }

                if (((KeyPressAction) action).code.equals("S")) {
                    state.getPlayer(request.playerName).pos.add(new Vector(0, 10));
                }

                if (((KeyPressAction) action).code.equals("D")) {
                    state.getPlayer(request.playerName).pos.add(new Vector(10, 0));
                }
            }
        }

        request.respond(new GameUpdateResponse(state));
    }

}
