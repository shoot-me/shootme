package cz.vse.java.shootme.server.net.responses;

import java.util.HashMap;
import java.util.Map;

public class GetConfigurationsResponse extends Response {

    public final Map<Integer, String> configurations = new HashMap<>();

}
