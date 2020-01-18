package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.common.game.State;

import java.util.UUID;

public class GameUpdateResponse extends Response {

    public final String id = UUID.randomUUID().toString();

    public final State state;

    public GameUpdateResponse(State state) {
        this.state = state.clone();
    }

}
