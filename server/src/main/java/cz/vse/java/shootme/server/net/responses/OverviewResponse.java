package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.server.game.Configuration;

import java.util.List;

public class OverviewResponse extends Response {

    public final List<Configuration> configurations;

    public OverviewResponse(List<Configuration> configurations) {
        this.configurations = configurations;
    }

}
