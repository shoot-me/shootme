package cz.vse.java.shootme.server.net.responses;

import cz.vse.java.shootme.server.game.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverviewResponse extends Response {

    public final List<Configuration> configurations = new ArrayList<>();

    public final Map<String, String> skins = new HashMap<>();

}
