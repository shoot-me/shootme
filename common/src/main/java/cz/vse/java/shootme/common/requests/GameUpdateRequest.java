package cz.vse.java.shootme.common.requests;

import cz.vse.java.shootme.common.game.actions.Action;

import java.util.List;

public class GameUpdateRequest extends Request {

    public final String gameName;

    public final String playerName;

    public final List<Action> actions;

    public GameUpdateRequest(String gameName, String playerName, List<Action> actions) {
        this.gameName = gameName;
        this.playerName = playerName;
        this.actions = actions;
    }

}
