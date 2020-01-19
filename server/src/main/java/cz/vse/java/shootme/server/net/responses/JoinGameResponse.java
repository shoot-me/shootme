package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.server.game.Configuration;

public class JoinGameResponse extends Response {

    public final int port;

    public final Configuration configuration;

    public JoinGameResponse(int port, Configuration configuration) {
        this.port = port;
        this.configuration = configuration;
    }

}
