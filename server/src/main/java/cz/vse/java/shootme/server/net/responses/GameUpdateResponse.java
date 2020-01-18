package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.server.game.State;

public class GameUpdateResponse extends Response {

    public final State state;

    public GameUpdateResponse(State state) {
        this.state = state;
    }

}
