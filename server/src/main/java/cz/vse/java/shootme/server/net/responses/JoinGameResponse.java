package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.State;

public class JoinGameResponse extends Response {

    public final Configuration configuration;

    public final State state;

    public JoinGameResponse(Configuration configuration, State state) {
        this.configuration = configuration;
        this.state = state;
    }

}
